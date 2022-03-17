package tech.coner.trailer.cli.command.person

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import tech.coner.trailer.Person
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class PersonSetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set",
        help = "Set a person"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
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
            names = arrayOf("--motorsportreg-member-id"),
            help = "Set the motorsportreg member ID to VALUE. Pass \"null\" to unset."
    )
            .convert { when {
                it.equals("null", ignoreCase = true) -> MotorsportRegMemberIdOption.Unset
                else -> MotorsportRegMemberIdOption.Set(it)
            } }

    override fun run() = diContext.use {
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