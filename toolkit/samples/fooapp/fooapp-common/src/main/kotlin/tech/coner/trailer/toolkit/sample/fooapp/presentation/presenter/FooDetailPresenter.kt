package tech.coner.trailer.toolkit.sample.fooapp.presentation.presenter

import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import tech.coner.trailer.toolkit.presentation.model.Loadable
import tech.coner.trailer.toolkit.presentation.model.whenLoadedSuccess
import tech.coner.trailer.toolkit.presentation.presenter.ItemModelPresenter
import tech.coner.trailer.toolkit.presentation.presenter.LoadablePresenter
import tech.coner.trailer.toolkit.presentation.presenter.PresenterCoroutineScope
import tech.coner.trailer.toolkit.presentation.presenter.StatefulPresenter
import tech.coner.trailer.toolkit.presentation.state.StateContainer
import tech.coner.trailer.toolkit.presentation.state.mutableLoadedProperty
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.service.FooService
import tech.coner.trailer.toolkit.sample.fooapp.presentation.adapter.FooEntityModelAdapter
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooItemModel
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooModel
import tech.coner.trailer.toolkit.sample.fooapp.presentation.state.FooDetailState
import kotlin.time.Duration.Companion.seconds

class FooDetailPresenter(
    initialState: FooDetailState = FooDetailState(loadable = Loadable.Empty()),
    private val argument: Foo.Id,
    private val adapter: FooEntityModelAdapter,
    private val service: FooService,
    coroutineScope: PresenterCoroutineScope
) : LoadablePresenter<FooService.FindFailure, FooItemModel>,
    StatefulPresenter<FooDetailState>,
    ItemModelPresenter,
    CoroutineScope by coroutineScope {

    private val stateContainer = StateContainer(initialState)
    override val state: FooDetailState get() = stateContainer.state
    override val stateFlow: StateFlow<FooDetailState> get() = stateContainer.stateFlow

    override suspend fun load(): Deferred<Result<Either<FooService.FindFailure, FooItemModel>>> = coroutineScope {
        async {
            runCatching {
                stateContainer.update { it.copy(loadable = Loadable.Loading()) }
                either {
                    service.findByKey(argument).getOrThrow()
                        .map { foo ->
                            // faking partially loaded with delay
                            delay(1.seconds)
                            val partial =  FooItemModel(
                                adapter.entityToModelAdapter(
                                    foo.copy(name = "${foo.name[0]}")
                                )
                            )
                            stateContainer.update { it.copy(loadable = Loadable.Loading(partial)) }
                            delay(1.seconds)
                            foo
                        }
                        .map { foo ->
                            FooItemModel(adapter.entityToModelAdapter(foo))
                                .also { fooItemModel -> stateContainer.update { it.copy(loadable = Loadable.Loaded(Either.Right(fooItemModel))) } }
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
        updateFn = { copy(name = adapter.entityToModelNamePropertyAdapter(it)) },
        property = FooModel::name
    )
}
