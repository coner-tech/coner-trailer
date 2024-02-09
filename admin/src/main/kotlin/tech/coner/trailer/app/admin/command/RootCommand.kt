package tech.coner.trailer.app.admin.command

import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import com.github.ajalt.mordant.terminal.ConversionResult
import java.nio.file.Path
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
import tech.coner.trailer.presentation.di.Format

class RootCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "coner-trailer-admin"
), DIAware by di {

    private val configurationServiceFactory: ConfigurationServiceFactory by factory()

    private val configDir: Path? by option(
        help = """
            Override the configuration directory. 
            This is mainly for development/testing. 
            The app will choose a sensible location automatically if omitted.
            """.trimIndent(),
        envvar = "CONER_TRAILER_CONFIG_DIR"
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
                """.trimMargin(),
        envvar = "CONER_TRAILER_DATABASE"
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

    private val format by option()
        .choice(
            "text" to Format.TEXT,
            "json" to Format.JSON
        )
        .default(Format.TEXT)

    private val verbose: Boolean by option().flag()

    override suspend fun CoroutineScope.coRun() {
        // TODO: first-run setup
        global.verbose = verbose
        val configurationServiceArgument = configDir?.let { ConfigurationServiceArgument.Override(it) }
            ?: ConfigurationServiceArgument.Default
        val configurationService: ConfigurationService =
            configurationServiceFactory(configurationServiceArgument)
        configurationService.init()
        val dbConfig = database
            ?.let { dbName ->
                configurationService.findDatabaseByName(dbName)
                    .getOrThrow()
            }
            ?: configurationService.getDefaultDatabase().getOrThrow()
        currentContext.invokedSubcommand?.also { subcommand ->
            if (dbConfig == null && subcommand !is PermitNoDatabaseChosen) {
                throw NoDatabaseChosenException()
            }
        }
        global.format = format
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
                ?: terminal.prompt(
                    prompt = "MotorsportReg Username"
                ) {
                    if (it.isNotBlank()) ConversionResult.Valid(it)
                    else ConversionResult.Invalid("Missing MotorsportReg Username")
                }!!,
            password = motorsportReg?.password
                ?: terminal.prompt(
                    prompt = "MotorsportReg Password",
                    hideInput = true
                ) {
                    if (it.isNotBlank()) ConversionResult.Valid(it)
                    else ConversionResult.Invalid("Missing MotorsportReg Password")
                }!!,
            organizationId = motorsportReg?.organizationId
                ?: database?.motorsportReg?.organizationId
                ?: terminal.prompt(
                    prompt = "MotorsportReg Organization ID"
                ) {
                    if (it.isNotBlank()) ConversionResult.Valid(it)
                    else ConversionResult.Invalid("Missing MotorsportReg Organization ID")
                }!!
        )
    }
}