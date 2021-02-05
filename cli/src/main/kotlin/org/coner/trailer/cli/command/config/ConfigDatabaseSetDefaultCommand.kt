package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class ConfigDatabaseSetDefaultCommand(
    di: DI
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
), DIAware {

    override val di: DI by findOrSetObject { di }
    private val service: ConfigurationService by instance()

    private val name: String by argument()

    override fun run() {
        val dbConfig = service.listDatabasesByName()[name]
        if (dbConfig == null) {
            echo("No database found with name")
            throw Abort()
        }
        val asDefault = dbConfig.copy(
                default = true
        )
        service.configureDatabase(asDefault)
    }
}