package org.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.SeasonTableView
import org.coner.trailer.io.service.SeasonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class SeasonListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List Seasons"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: SeasonService by instance()
    private val view: SeasonTableView by instance()

    override fun run() = diContext.use {
        echo(view.render(service.list()))
    }
}