package tech.coner.trailer.presentation.presenter.person

import kotlinx.coroutines.CoroutineScope
import tech.coner.trailer.Person
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.io.util.runSuspendCatching
import tech.coner.trailer.presentation.adapter.PersonDetailModelAdapter
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.presenter.BaseItemPresenter
import tech.coner.trailer.presentation.presenter.PresenterCoroutineScope
import java.util.*

class PersonDetailPresenter(
    private val argument: Argument,
    coroutineScope: PresenterCoroutineScope,
    private val service: PersonService,
    override val adapter: PersonDetailModelAdapter,
) : BaseItemPresenter<
        Person,
        PersonDetailModelAdapter,
        PersonDetailModel,
        >(), CoroutineScope by coroutineScope {

    override val entityDefault: Person = Person(
        clubMemberId = "",
        firstName = "",
        lastName = "",
        motorsportReg = null
    )

    override suspend fun performLoad(): Result<Person> = runSuspendCatching {
        service.findById(argument.id)
    }

    data class Argument(
        val id: UUID
    )
}

typealias PersonDetailPresenterFactory = (PersonDetailPresenter.Argument) -> PersonDetailPresenter
