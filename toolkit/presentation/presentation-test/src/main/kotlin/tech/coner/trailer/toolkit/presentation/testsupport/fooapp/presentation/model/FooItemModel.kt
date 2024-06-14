package tech.coner.trailer.toolkit.presentation.testsupport.fooapp.presentation.model

import kotlinx.coroutines.runBlocking
import tech.coner.trailer.toolkit.presentation.model.BaseItemModel
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.constraint.FooConstraint
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.presentation.testsupport.fooapp.presentation.adapter.FooAdapter

class FooItemModel(
    override val initialItem: Foo,
    private val adapter: FooAdapter,
) : BaseItemModel<Foo, FooConstraint>() {
    override val constraints =
        FooConstraint()

    var name: String
        get() = adapter.modelNameProperty(pendingItem)
        set(value) = runBlocking { mutatePendingItem { it.copy(name = adapter.entityNameProperty(value)) } }

}

