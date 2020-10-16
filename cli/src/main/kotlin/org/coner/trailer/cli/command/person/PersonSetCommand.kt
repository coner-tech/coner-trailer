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
    private val clubMemberId: MemberIdOption? by option(
            "--club-member-id",
            help = "Set the club member ID to VALUE. Pass \"null\" to unset."
    )
            .convert { when {
                it.equals("null", ignoreCase = true) -> MemberIdOption.Unset
                else -> MemberIdOption.Set(it)
            } }
    private val firstName: String? by option()
    private val lastName: String? by option()
    sealed class MotorsportRegMemberIdOption {
        data class Set(val value: String): MotorsportRegMemberIdOption()
        object Unset : MotorsportRegMemberIdOption()
    }
    private val motorsportRegMemberId: MotorsportRegMemberIdOption? by option(
            help = "Set the motorsportreg member ID to VALUE. Pass \"null\" to unset."
    )
            .convert { when {
                it.equals("null", ignoreCase = true) -> MotorsportRegMemberIdOption.Unset
                else -> MotorsportRegMemberIdOption.Set(it)
            } }

    override fun run() {
        val current = service.findById(id)
        val set = Person(
                id = current.id,
                clubMemberId = when (val memberId = clubMemberId) {
                    is MemberIdOption.Set -> memberId.value
                    is MemberIdOption.Unset -> null
                    null -> current.clubMemberId
                },
                firstName = firstName ?: current.firstName,
                lastName = lastName ?: current.lastName,
                motorsportReg = when (val motorsportRegMemberId = motorsportRegMemberId) {
                    is MotorsportRegMemberIdOption.Set -> Person.MotorsportRegMetadata(motorsportRegMemberId.value)
                    is MotorsportRegMemberIdOption.Unset -> null
                    else -> current.motorsportReg
                }
        )
        service.update(set)
        echo(view.render(set))
    }
}