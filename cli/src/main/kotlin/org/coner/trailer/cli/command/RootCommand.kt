package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.cli.di.ConfigurationServiceArgument
import org.coner.trailer.cli.di.ConfigurationServiceFactory
import org.coner.trailer.cli.di.databaseServiceModule
import org.coner.trailer.cli.io.ConfigurationService
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
            if (database != service.noDatabase) {
                bind<ConfigurationService>() with instance(service)
                import(databaseServiceModule(databaseConfiguration = database))
            }
        }
    }

    /**
     * Direct subcommands may implement this to bypass the requirement to choose a database.
     *
     * This comes into play when no database exists yet (during a first-run, etc), or no
     * default is available and no choice was made.
     */
    interface PermitNoDatabaseChosen

}