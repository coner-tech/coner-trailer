package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
import tech.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.di.ConfigurationServiceFactory
import tech.coner.trailer.di.EnvironmentHolderImpl
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.service.ConfigurationService
import java.nio.file.Path

class RootCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "coner-trailer-cli"
), DIAware by di {

    private val configurationServiceFactory: ConfigurationServiceFactory by factory()

    private val configDir: Path? by option(
        help = """
            Override the configuration directory. 
            This is mainly for development/testing. 
            The app will choose a sensible location automatically if omitted.
            """.trimIndent()
    )
        .path(
            mustExist = true,
            canBeFile = false,
            canBeDir = true,
            mustBeWritable = true,
            mustBeReadable = true,
            canBeSymlink = false
        )

    private val database: String? by option(
        help = """
                |Name of the database to use instead of the default.
                |   Will use the default configured database if not specified. 
                |   See: coner-trailer-cli config database
                """.trimMargin()
    )
    private val motorsportReg: MotorsportRegCredentials? by MotorsportRegCredentials()
    class MotorsportRegCredentials : OptionGroup() {
        val username: String? by option(
            envvar = "MOTORSPORTREG_USERNAME",
            names = arrayOf("--motorsportreg-username")
        )
        val password: String? by option(
            envvar = "MOTORSPORTREG_PASSWORD",
            names = arrayOf("--motorsportreg-password")
        )
        val organizationId: String? by option(
            envvar = "MOTORSPORTREG_ORGANIZATION_ID",
            names = arrayOf("--motorsportreg-organization-id")
        )
    }

    private val verbose: Boolean by option().flag()

    override suspend fun CoroutineScope.coRun() {
        // TODO: first-run setup
        global.verbose = verbose
        val configurationServiceArgument = configDir?.let { ConfigurationServiceArgument.Override(it) }
            ?: ConfigurationServiceArgument.Default
        val configurationService: ConfigurationService =
            this@RootCommand.configurationServiceFactory(configurationServiceArgument)
        configurationService.init()
        val dbConfig = database
            ?.let { dbName ->
                configurationService.findDatabaseByName(dbName)
                    .getOrElse {
                        echo("Failed to find database by name: ${it.message}", err = true)
                        throw ProgramResult(1)
                    }
            }
            ?: configurationService.getDefaultDatabase().getOrThrow()
        currentContext.invokedSubcommand?.also { subcommand ->
            if (dbConfig == null && subcommand !is PermitNoDatabaseChosen) {
                echo(
                    message = "Command requires database but no database was selected. See: coner-trailer-cli config database",
                    err = true
                )
                throw Abort()
            }
        }
        global.environment = EnvironmentHolderImpl(
            di = di,
            configurationServiceArgument = configurationServiceArgument,
            configuration = configurationService.get().getOrThrow(),
            databaseConfiguration = dbConfig,
            motorsportRegCredentialSupplier = { assembleMotorsportRegBasicCredentials(dbConfig) }
        )
    }

    private fun assembleMotorsportRegBasicCredentials(database: DatabaseConfiguration?): MotorsportRegBasicCredentials {
        return MotorsportRegBasicCredentials(
            username = motorsportReg?.username
                ?: database?.motorsportReg?.username
                ?: prompt(
                    text = "MotorsportReg Username"
                ) {
                    it.ifBlank { throw UsageError("Missing MotorsportReg Username") }
                }!!,
            password = motorsportReg?.password
                ?: prompt(
                    text = "MotorsportReg Password",
                    hideInput = true
                ) {
                    it.ifBlank { throw UsageError("Missing MotorsportReg Password") }
                }!!,
            organizationId = motorsportReg?.organizationId
                ?: database?.motorsportReg?.organizationId
                ?: prompt(
                    text = "MotorsportReg Organization ID"
                ) {
                    it.ifBlank { throw UsageError("Missing MotorsportReg Organization ID") }
                }!!
        )
    }
}