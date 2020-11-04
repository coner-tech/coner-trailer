package org.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.MotorsportRegMemberTableView
import org.coner.trailer.io.service.MotorsportRegMemberService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MotorsportRegMemberListCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "list",
        help = "List members from MotorsportReg"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: MotorsportRegMemberService by instance()
    private val view: MotorsportRegMemberTableView by instance()

    override fun run() {
        val members = service.list()
        echo(view.render(members))
    }
}