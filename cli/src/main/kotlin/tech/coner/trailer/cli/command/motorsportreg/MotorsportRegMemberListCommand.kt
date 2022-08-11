package tech.coner.trailer.cli.command.motorsportreg

import org.kodein.di.DI
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.MotorsportRegMemberTableView
import tech.coner.trailer.io.service.MotorsportRegMemberService

class MotorsportRegMemberListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "list",
        help = "List members from MotorsportReg"
) {

    override val diContext = diContextDataSession()
    private val service: MotorsportRegMemberService by instance()
    private val view: MotorsportRegMemberTableView by instance()

    override suspend fun coRun() = diContext.use {
        val members = service.list()
        echo(view.render(members))
    }
}