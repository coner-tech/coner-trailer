package org.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.MotorsportRegMemberTableView
import org.coner.trailer.cli.view.MotosportRegMemberView
import org.coner.trailer.io.service.MotorsportRegService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MotorsportRegMemberFetchCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "fetch",
        help = "Fetch (display) members from MotorsportReg"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: MotorsportRegService by instance()
    private val view: MotorsportRegMemberTableView by instance()

    override fun run() {
        val members = service.fetchMembers()
        echo(view.render(members))
    }
}