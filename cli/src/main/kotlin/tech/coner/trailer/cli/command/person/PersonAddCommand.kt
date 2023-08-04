package tech.coner.trailer.cli.command.person

import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import java.util.*

class PersonAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "add",
        help = "Add a person"
) {

    override val diContext = diContextDataSession()
    private val service: PersonService by instance()
    private val adapter: Adapter<Person, PersonDetailModel> by instance()
    private val view: TextView<PersonDetailModel> by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val clubMemberId: String? by option()
    private val firstName: String by option().required()
    private val lastName: String by option().required()
    private val motorsportregMemberId: String? by option()

    override suspend fun CoroutineScope.coRun() = diContext.use {
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
        echo(view(adapter(create)))
    }
}