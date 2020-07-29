package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.sources.ExperimentalValueSourceApi
import com.github.ajalt.clikt.sources.PropertiesValueSource
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class RootCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "coner-trailer"
) {

    val database: DatabaseConfiguration? by option(
            help = """
                |Name of the database to use.
                |   Will use the default configured database if none given. 
                |   See: coner-trailer config database
                """.trimMargin()
    )
            .choice(config.listDatabases().map { it.name to it }.toMap())

    @OptIn(ExperimentalValueSourceApi::class)
    override fun run() {
        config.setup()
        currentContext.obj = Payload(
                databaseConfiguration = database
                        ?: config.getDefaultDatabase()
        )
    }

    class Payload(
            val databaseConfiguration: DatabaseConfiguration? = null
    )
}