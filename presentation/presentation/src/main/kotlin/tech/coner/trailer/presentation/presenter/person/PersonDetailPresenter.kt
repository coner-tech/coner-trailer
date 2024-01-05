package tech.coner.trailer.presentation.presenter.person

import kotlinx.coroutines.CoroutineScope
import tech.coner.trailer.Person
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.io.util.runSuspendCatching
import tech.coner.trailer.presentation.adapter.PersonDetailModelAdapter
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.presenter.BaseItemPresenter
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.PresenterCoroutineScope
import java.util.*

class PersonDetailPresenter(
    override val argument: Argument,
    coroutineScope: PresenterCoroutineScope,
    private val service: PersonService,
    override val adapter: PersonDetailModelAdapter,
) : BaseItemPresenter<
        PersonDetailPresenter.Argument,
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

    init {
        when (argument) {
            Argument.Create -> { /* no-op */ }
            is Argument.GetById -> {
                itemModel.setId(argument.id)
                commit()
            }
        }
    }

    override suspend fun performLoad(): Result<Person> = runSuspendCatching {
        service.findById(itemModel.original.id)
    }

    suspend fun create() = runSuspendCatching {
        service.create(itemModel.original)
    }

    suspend fun delete() = runSuspendCatching {
        service.delete(itemModel.original)
    }

    sealed class Argument : Presenter.Argument {
        object Create : Argument()
        data class GetById(val id: UUID) : Argument()
    }
}

typealias PersonDetailPresenterFactory = (PersonDetailPresenter.Argument) -> PersonDetailPresenter
