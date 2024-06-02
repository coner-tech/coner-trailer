package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.presenter

import kotlin.coroutines.CoroutineContext
import tech.coner.trailer.presentation.library.presenter.LoadableItemPresenter
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
    }
}