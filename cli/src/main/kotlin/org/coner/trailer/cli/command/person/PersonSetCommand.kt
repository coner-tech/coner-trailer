package org.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.Person
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class PersonSetCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "set",
        help = "Set a person"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: PersonService by instance()
    private val view: PersonView by instance()

    private val id: UUID by argument()
            .convert { toUuid(it) }
    sealed class MemberIdOption {
        data class Set(val value: String) : MemberIdOption()
        object Unset : MemberIdOption()
    }
    private val memberId: MemberIdOption? by option(
            "--member-id",
            help = "Set the member ID to VALUE. Pass \"null\" to unset."
    )
            .convert { when {
                it.equals("null", ignoreCase = true) -> MemberIdOption.Unset
                else -> MemberIdOption.Set(it)
            } }
    private val firstName: String? by option()
    private val lastName: String? by option()

    override fun run() {
        val current = service.findById(id)
        val set = Person(
                id = current.id,
                memberId = when (val memberId = memberId) {
                    is MemberIdOption.Set -> memberId.value
                    is MemberIdOption.Unset -> null
                    null -> current.memberId
                },
                firstName = firstName ?: current.firstName,
                lastName = lastName ?: current.lastName
        )
        service.update(set)
        echo(view.render(set))
    }
}