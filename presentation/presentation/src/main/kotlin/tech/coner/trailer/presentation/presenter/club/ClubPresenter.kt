package tech.coner.trailer.presentation.presenter.club

import kotlinx.coroutines.CoroutineScope
import tech.coner.snoozle.db.entity.EntityIoException
import tech.coner.trailer.Club
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.util.runSuspendCatching
import tech.coner.trailer.presentation.adapter.ClubModelAdapter
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.presenter.BaseItemPresenter
import tech.coner.trailer.presentation.presenter.Presenter
import tech.coner.trailer.presentation.presenter.PresenterCoroutineScope

class ClubPresenter(
    override val argument: Presenter.Argument.Nothing,
    coroutineScope: PresenterCoroutineScope,
    private val service: ClubService,
    override val adapter: ClubModelAdapter
) : BaseItemPresenter<Presenter.Argument.Nothing, Club, ClubModelAdapter, ClubModel>(),
    CoroutineScope by coroutineScope {

    override fun processArgument() = Unit // no-op

    override val entityDefault = Club("")

    override suspend fun performLoad(): Result<Club> = runSuspendCatching {
        try {
            service.get()
        } catch (notFound: EntityIoException.NotFound) {
            entityDefault
        }
    }

    suspend fun createOrUpdate() {
        service.createOrUpdate(name = itemModel.itemValue.name)
    }
}

typealias ClubPresenterFactory = (Presenter.Argument.Nothing) -> ClubPresenter