package tech.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.io.service.PersonService
import java.util.*

class PersonGetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "get",
        help = "Get a person"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: PersonService by instance()
    private val view: PersonView by instance()

    private val id: UUID by argument()
            .convert { toUuid(it) }

    override fun run() = diContext.use {
        val get = service.findById(id)
        echo(view.render(get))
    }
}