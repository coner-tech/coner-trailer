package tech.coner.trailer.app.admin.command.person

import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.app.admin.util.succeedOrThrow
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenterFactory
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
    private val presenterFactory: PersonDetailPresenterFactory by factory()
    private val view: TextView<PersonDetailModel> by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val clubMemberId: String? by option()
    private val firstName: String by option().required()
    private val lastName: String by option().required()
    private val motorsportregMemberId: String? by option()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val presenter = presenterFactory(PersonDetailPresenter.Argument.Create)
        val model = presenter.itemModel

        model.setId(id)
        clubMemberId?.run { model.setClubMemberId(this) }
        model.setFirstName(firstName)
        model.setLastName(lastName)
        motorsportregMemberId?.run { model.setMotorsportRegId(this) }
        presenter.commit()
            .succeedOrThrow {
                presenter.create()
                echo(view(it))
            }
    }
}