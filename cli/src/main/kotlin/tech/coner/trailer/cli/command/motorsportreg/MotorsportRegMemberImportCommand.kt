package tech.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.render.view.PersonCollectionViewRenderer

class MotorsportRegMemberImportCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "import",
        help = "Import people from MotorsportReg Member records"
) {

    override val diContext = diContextDataSession()
    private val service: MotorsportRegImportService by instance()
    private val view: PersonCollectionViewRenderer by instance(Format.TEXT)

    private val dryRun: Boolean by option(
            help = "Simulate and display what would be changed, but don't persist any changes"
    ).flag()

    override suspend fun coRun() = diContext.use {
        val result = service.importMembersAsPeople(dryRun)
        echo("Created: (${result.created.size})")
        echo(view.render(result.created))
        echo()
        echo("Updated: (${result.updated.size})")
        echo(view.render(result.updated))
    }
}