package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model

import kotlin.coroutines.CoroutineContext
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint.FooConstraint
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter.FooAdapter

class FooItemModel(
    override val initialItem: Foo,
    private val adapter: FooAdapter,
    override val coroutineContext: CoroutineContext
) : BaseItemModel<Foo, FooConstraint>() {
    override val constraints = FooConstraint()

    var name: String
        get() = adapter.modelNameProperty(pendingItem)
        set(value) = update { it.copy(name = value) }

}

