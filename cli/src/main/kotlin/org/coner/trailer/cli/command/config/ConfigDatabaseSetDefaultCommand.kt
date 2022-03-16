package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.io.ConfigurationService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class ConfigDatabaseSetDefaultCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set-default",
        help = "Set named database to default"
), DIAware {

    override val diContext = diContext { global.requireEnvironment() }
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