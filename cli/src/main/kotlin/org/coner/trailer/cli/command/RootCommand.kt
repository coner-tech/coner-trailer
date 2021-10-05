package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.cli.di.ConfigurationServiceArgument
import org.coner.trailer.cli.di.ConfigurationServiceFactory
import org.coner.trailer.cli.di.motorsportRegApiModule
import org.coner.trailer.client.motorsportreg.MotorsportRegBasicCredentials
import org.coner.trailer.di.databaseModule
import org.coner.trailer.di.eventResultsModule
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.io.DatabaseConfiguration
import org.kodein.di.*
import java.nio.file.Path

class RootCommand(override val di: DI) : CliktCommand(
    name = "coner-trailer-cli"
), DIAware {

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

    private val serviceFactory: ConfigurationServiceFactory by factory()

    override fun run() {
        // TODO: first-run setup
        val service: ConfigurationService = serviceFactory(
            configDir?.let { ConfigurationServiceArgument.Override(it) }
                ?: ConfigurationServiceArgument.Default
        )
        service.setup()
        val database = database?.let {
            val database = service.listDatabasesByName()[it]
            if (database == null) {
                echo(
                    message = "Database not found",
                    err = true
                )
                throw Abort()
            }
            database
        }
            ?: service.getDefaultDatabase()
            ?: service.noDatabase
        currentContext.invokedSubcommand?.also { subcommand ->
            if (database == service.noDatabase && subcommand !is PermitNoDatabaseChosen) {
                echo(
                    message = "No database chosen and no default configured. See: coner-trailer-cli config database",
                    err = true
                )
                throw Abort()
            }
        }
        currentContext.obj = DI {
            extend(di, copy = Copy.All)
            bind<ConfigurationService>() with instance(service)
            if (database != service.noDatabase) {
                import(databaseModule(databaseConfiguration = database))
                import(eventResultsModule)
                import(motorsportRegApiModule { assembleMotorsportRegBasicCredentials(database) })
            }
        }
    }

    private fun assembleMotorsportRegBasicCredentials(database: DatabaseConfiguration): MotorsportRegBasicCredentials {
        return MotorsportRegBasicCredentials(
            username = motorsportReg?.username
                ?: database.motorsportReg?.username
                ?: prompt(
                    text = "MotorsportReg Username"
                ) {
                    if (it.isNotBlank()) it else throw UsageError("Missing MotorsportReg Username")
                }!!,
            password = motorsportReg?.password
                ?: prompt(
                    text = "MotorsportReg Password",
                    hideInput = true
                ) {
                    if (it.isNotBlank()) it else throw UsageError("Missing MotorsportReg Password")
                }!!,
            organizationId = motorsportReg?.organizationId
                ?: database.motorsportReg?.organizationId
                ?: prompt(
                    text = "MotorsportReg Organization ID"
                ) {
                    if (it.isNotBlank()) it else throw UsageError("Missing MotorsportReg Organization ID")
                }!!
        )
    }
}