package org.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.MotorsportRegMemberTableView
import org.coner.trailer.io.service.MotorsportRegMemberService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class MotorsportRegMemberListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List members from MotorsportReg"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: MotorsportRegMemberService by instance()
    private val view: MotorsportRegMemberTableView by instance()

    override fun run() = diContext.use {
        val members = service.list()
        echo(view.render(members))
    }
}