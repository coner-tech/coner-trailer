package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration

class ConfigDatabaseSetDefaultCommand(
        private val service: ConfigurationService
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
) {

    private val dbConfig: DatabaseConfiguration by databaseNameOption(service)
            .required()

    override fun run() {
        if (dbConfig == service.noDatabase) throw Abort()
        val asDefault = dbConfig.copy(
                default = true
        )
        service.configureDatabase(asDefault)
    }
}