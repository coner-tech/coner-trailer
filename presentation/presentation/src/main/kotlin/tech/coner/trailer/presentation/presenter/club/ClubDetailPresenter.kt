package tech.coner.trailer.presentation.presenter.club

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.util.runSuspendCatching
import tech.coner.trailer.presentation.model.club.ClubDetailItemModel
import tech.coner.trailer.presentation.model.club.ClubDetailModel
import tech.coner.trailer.presentation.model.club.ClubEntityModelAdapter
import tech.coner.trailer.presentation.state.club.ClubDetailState
import tech.coner.trailer.toolkit.presentation.model.Loadable
import tech.coner.trailer.toolkit.presentation.model.whenLoadedSuccess
import tech.coner.trailer.toolkit.presentation.presenter.ItemModelPresenter
import tech.coner.trailer.toolkit.presentation.presenter.LoadablePresenter
import tech.coner.trailer.toolkit.presentation.presenter.PresenterCoroutineScope
import tech.coner.trailer.toolkit.presentation.presenter.StatefulPresenter
import tech.coner.trailer.toolkit.presentation.state.StateContainer
import tech.coner.trailer.toolkit.presentation.state.mutableLoadedProperty

class ClubDetailPresenter(
    initialState: ClubDetailState = ClubDetailState(loadable = Loadable.Empty()),
    private val adapter: ClubEntityModelAdapter,
    private val service: ClubService,
    coroutineScope: PresenterCoroutineScope
) : LoadablePresenter<ClubService.GetFailure, ClubDetailItemModel>,
    StatefulPresenter<ClubDetailState>,
    ItemModelPresenter,
    CoroutineScope by coroutineScope {

    private val stateContainer = StateContainer(initialState)
    override val state: ClubDetailState get() = stateContainer.state
    override val stateFlow: StateFlow<ClubDetailState> get() = stateContainer.stateFlow

    override suspend fun load(): Deferred<Result<Either<ClubService.GetFailure, ClubDetailItemModel>>> = coroutineScope {
        async {
            runSuspendCatching {
                stateContainer.update { it.copy(loadable = Loadable.Loading()) }
                either {
                    service.get().getOrThrow()
                        .map { club ->
                            ClubDetailItemModel(
                                initialItem = adapter.entityToModelAdapter(club),
                                adapter = adapter
                            )
                        }
                        .onLeft { failure -> stateContainer.update { it.copy(loadable = Loadable.Loaded(Either.Left(failure))) } }
                        .bind()

                }
            }
                .onFailure { throwable ->
                    stateContainer.update { it.copy(loadable = Loadable.FailedExceptionally(throwable)) }
                }
        }
    }


    override suspend fun commit() {
        state.loadable.whenLoadedSuccess { it.commit() }
    }

    override suspend fun validate() {
        state.loadable.whenLoadedSuccess { it.validate() }
    }

    override suspend fun reset() {
        state.loadable.whenLoadedSuccess { it.reset() }
    }

    val name = stateContainer.mutableLoadedProperty(
        getFn = { name },
        updateFn = { copy(name = it) },
        property = ClubDetailModel::name
    )
}