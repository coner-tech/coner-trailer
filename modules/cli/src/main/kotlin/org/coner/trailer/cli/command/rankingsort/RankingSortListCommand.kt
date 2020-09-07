package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.io.service.RankingSortService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class RankingSortListCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "list",
        help = "List ranking sorts"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}