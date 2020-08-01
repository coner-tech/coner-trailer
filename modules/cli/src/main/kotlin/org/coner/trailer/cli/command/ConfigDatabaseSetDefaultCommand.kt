package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class ConfigDatabaseSetDefaultCommand(
        private val config: ConfigurationService
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
) {

    val dbConfig: DatabaseConfiguration by option(names = *arrayOf("--name"))
            .choice(config.listDatabasesByName())
            .required()

    override fun run() {
        if (dbConfig == config.noDatabase) throw Abort()
        val asDefault = dbConfig.copy(
                default = true
        )
        config.configureDatabase(asDefault)
    }
}