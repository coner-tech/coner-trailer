package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.Person
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class PersonAddCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "add",
        help = "Add a person"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: PersonService by instance()
    private val view: PersonView by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val clubMemberId: String? by option()
    private val firstName: String by option().required()
    private val lastName: String by option().required()
    private val motorsportregMemberId: String? by option()

    override fun run() = diContext.use {
        val create = Person(
                id = id,
                clubMemberId = clubMemberId,
                firstName = firstName,
                lastName = lastName,
                motorsportReg = motorsportregMemberId?.let { Person.MotorsportRegMetadata(
                        memberId = it
                ) }
        )
        service.create(create)
        echo(view.render(create))
    }
}