package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.di.serviceModule
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class RootCommand(
        override val di: DI
) : CliktCommand(
        name = "coner-trailer"
), DIAware {

    private val config: ConfigurationService by instance()
    private val noDatabase: DatabaseConfiguration by instance(NoDatabase)

    init {
        config.setup()
    }

    private val databasesByName = config.listDatabasesByName().let {
        if (it.isNotEmpty()) {
            it
        } else {
            mapOf(noDatabase.name to noDatabase)
        }
    }

    val database: DatabaseConfiguration by option(
            help = """
                |Name of the database to use instead of the default.
                |   Will use the default configured database if not specified. 
                |   See: coner-trailer config database
                """.trimMargin()
    )
            .choice(databasesByName)
            .default(config.getDefaultDatabase() ?: noDatabase)

    override fun run() {
        // TODO: first-run setup
        currentContext.invokedSubcommand?.also { subcommand ->
            if (database == noDatabase && subcommand !is PermitNoDatabaseChosen) {
                echo("No database chosen and no default configured. See: coner-trailer config database")
                throw Abort()
            }
        }
        currentContext.obj = DI {
            extend(di)
            if (database != noDatabase) {
                import(serviceModule(databaseConfiguration = database))
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

    object NoDatabase
}