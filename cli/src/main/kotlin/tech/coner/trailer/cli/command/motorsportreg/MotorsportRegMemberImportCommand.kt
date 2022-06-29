package tech.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.PersonTableView
import tech.coner.trailer.io.service.MotorsportRegImportService

class MotorsportRegMemberImportCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "import",
        help = "Import people from MotorsportReg Member records"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: MotorsportRegImportService by instance()
    private val view: PersonTableView by instance()

    private val dryRun: Boolean by option(
            help = "Simulate and display what would be changed, but don't persist any changes"
    ).flag()

    override fun run() = diContext.use {
        val result = service.importMembersAsPeople(dryRun)
        echo("Created: (${result.created.size})")
        echo(view.render(result.created))
        echo()
        echo("Updated: (${result.updated.size})")
        echo(view.render(result.updated))
    }
}