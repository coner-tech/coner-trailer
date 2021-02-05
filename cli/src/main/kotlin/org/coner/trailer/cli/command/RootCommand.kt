package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.cli.di.ConfigurationServiceArgument
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
                |   See: coner-trailer config database
                """.trimMargin()
    )

    private val configurationServiceFactory: (ConfigurationServiceArgument) -> ConfigurationService by factory()

    override fun run() {
        // TODO: first-run setup
        val config = configurationServiceFactory(
            configDir?.let { ConfigurationServiceArgument.Override(it) }
                ?: ConfigurationServiceArgument.Default
        )
        config.setup()
        val database = database?.let { config.listDatabasesByName()[it] }
            ?: config.getDefaultDatabase()
            ?: config.noDatabase
        currentContext.invokedSubcommand?.also { subcommand ->
            if (database == config.noDatabase && subcommand !is PermitNoDatabaseChosen) {
                echo("No database chosen and no default configured. See: coner-trailer config database")
                throw Abort()
            }
        }
        currentContext.obj = DI {
            extend(di, copy = Copy.All)
            if (database != config.noDatabase) {
                bind<ConfigurationService>(overrides = true) with instance(config)
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