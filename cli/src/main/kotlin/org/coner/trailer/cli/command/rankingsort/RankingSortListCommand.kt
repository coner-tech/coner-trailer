package org.coner.trailer.cli.command.rankingsort

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.io.service.RankingSortService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class RankingSortListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List ranking sorts"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    override fun run() = diContext.use {
        echo(view.render(service.list()))
    }
}