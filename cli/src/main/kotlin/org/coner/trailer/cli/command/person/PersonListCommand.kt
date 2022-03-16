package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class PersonListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "list",
        help = "List all people"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: PersonService by instance()
    private val view: PersonView by instance()

    override fun run() = diContext.use {
        echo(view.render(service.list()))
    }
}