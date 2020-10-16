package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.Person
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class PersonAddCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "add",
        help = "Add a person"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: PersonService by instance()
    private val view: PersonView by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val clubMemberId: String? by option()
    private val firstName: String by option().required()
    private val lastName: String by option().required()

    override fun run() {
        val create = Person(
                id = id,
                clubMemberId = clubMemberId,
                firstName = firstName,
                lastName = lastName
        )
        service.create(create)
        echo(view.render(create))
    }
}