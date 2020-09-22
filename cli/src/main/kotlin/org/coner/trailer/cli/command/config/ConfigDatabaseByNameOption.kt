package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.io.ConfigurationService

fun CliktCommand.databaseNameOption(service: ConfigurationService) = option("--name")
        .choice(service.listDatabasesByName().let {
            if (it.isNotEmpty()) {
                it
            } else {
                mapOf(service.noDatabase.name to service.noDatabase)
            }
        })