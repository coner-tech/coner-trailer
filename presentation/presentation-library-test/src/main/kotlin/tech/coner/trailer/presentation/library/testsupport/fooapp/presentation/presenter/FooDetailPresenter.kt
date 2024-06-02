package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.presenter

import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import tech.coner.trailer.presentation.library.model.LoadableModel
import tech.coner.trailer.presentation.library.presenter.LoadableItemPresenter
import tech.coner.trailer.presentation.library.state.LoadableItemState
import tech.coner.trailer.presentation.library.testsupport.fooapp.data.service.FooService
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter.FooAdapter
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model.FooModel

class FooDetailPresenter(
    override val argument: Foo.Id,
    private val service: FooService,
    override val coroutineContext: CoroutineContext
) : LoadableItemPresenter<
        Foo.Id,
        Foo,
        Unit,
        FooModel
        >() {
    override val adapter = FooAdapter()

    override suspend fun performLoad(): Result<Foo> {
        return service.findById(argument)
            .onSuccess { foo ->
                // faking a partial load
                update {
                    LoadableItemState(
                        LoadableModel.Loading(
                            partial = adapter.itemToModelAdapter(
                                foo.copy(name = "${foo.name[0]}")
                            )
                        )
                    )
                }
                delay(1.seconds)
            }
    }
}