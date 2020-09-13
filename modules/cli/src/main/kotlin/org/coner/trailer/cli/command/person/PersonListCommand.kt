package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class PersonListCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "list",
        help = "List all people"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: PersonService by instance()
    private val view: PersonView by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}