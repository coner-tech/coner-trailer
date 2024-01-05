package tech.coner.trailer.presentation.presenter.club

import kotlinx.coroutines.CoroutineScope
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

    override val entityDefault = Club("")

    override suspend fun performLoad(): Result<Club> = runSuspendCatching {
        service.get()
    }

    suspend fun createOrUpdate() = runSuspendCatching {
        service.createOrUpdate(name = itemModel.itemValue.name).getOrThrow()
    }
}

typealias ClubPresenterFactory = (Presenter.Argument.Nothing) -> ClubPresenter