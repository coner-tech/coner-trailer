package org.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.cli.view.PersonTableView
import org.coner.trailer.io.service.MotorsportRegImportService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MotorsportRegMemberImportCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "import",
        help = "Import people from MotorsportReg Member records"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: MotorsportRegImportService by instance()
    private val view: PersonTableView by instance()

    private val dryRun: Boolean by option(
            help = "Simulate and display what would be changed, but don't persist any changes"
    ).flag()

    override fun run() {
        val result = service.importMembersAsPeople(dryRun)
        echo("Created: (${result.created.size})")
        echo(view.render(result.created))
        echo()
        echo("Updated: (${result.updated.size})")
        echo(view.render(result.updated))
    }
}