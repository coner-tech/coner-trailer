package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.sources.ExperimentalValueSourceApi
import com.github.ajalt.clikt.sources.PropertiesValueSource
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class RootCommand : CliktCommand(
        name = "coner-trailer"
) {

    val database: String? by option(
            help = """
                |Name of the database to use.
                |   Will use the default configured database if none given. 
                |   See: coner-trailer config database
                """.trimMargin()

    )
    private lateinit var config: ConfigurationService

    @OptIn(ExperimentalValueSourceApi::class)
    override fun run() {
        config = ConfigurationService()
        config.setup()
        currentContext.obj = Payload(
                databaseConfiguration = when (val dbName = database) {
                    is String -> {
                        val namedDatabaseConfiguration = config.getDatabase(dbName)
                        if (namedDatabaseConfiguration != null) {
                            namedDatabaseConfiguration
                        } else {
                            echo("No database found with name $database", err = true)
                            throw Abort(true)
                        }
                    }
                    else -> config.getDefaultDatabase()
                }
        )
    }

    class Payload(
            val databaseConfiguration: DatabaseConfiguration? = null
    )
}