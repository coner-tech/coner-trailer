package org.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.SeasonTableView
import org.coner.trailer.io.service.SeasonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class SeasonListCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "list",
        help = "List Seasons"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: SeasonService by instance()
    private val view: SeasonTableView by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}