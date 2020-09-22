package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.io.ConfigurationService

fun CliktCommand.databaseNameOption(
        service: ConfigurationService,
        vararg names: String = arrayOf("--name"),
        help: String = ""
) = option(names = names, help = help)
        .choice(service.listDatabasesByName().let {
            if (it.isNotEmpty()) {
                it
            } else {
                mapOf(service.noDatabase.name to service.noDatabase)
            }
        })