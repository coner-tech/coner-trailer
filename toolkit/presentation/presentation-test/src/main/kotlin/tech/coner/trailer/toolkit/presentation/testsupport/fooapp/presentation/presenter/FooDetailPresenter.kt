package tech.coner.trailer.toolkit.presentation.testsupport.fooapp.presentation.presenter

import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import tech.coner.trailer.toolkit.presentation.model.LoadableModel
import tech.coner.trailer.toolkit.presentation.presenter.LoadableItemPresenter
import tech.coner.trailer.toolkit.presentation.state.LoadableItemState
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.presentation.adapter.FooAdapter
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.presentation.model.FooItemModel

class FooDetailPresenter(
    override val argument: Foo.Id,
    private val service: tech.coner.trailer.toolkit.presentation.testsupport.fooapp.data.service.FooService,
    override val coroutineContext: CoroutineContext
) : LoadableItemPresenter<
        Foo.Id,
        Foo,
        Unit,
        FooItemModel
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