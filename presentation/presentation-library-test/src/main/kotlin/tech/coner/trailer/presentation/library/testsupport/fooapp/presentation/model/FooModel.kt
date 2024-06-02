package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint.FooConstraint
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo
import tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.adapter.FooAdapter

class FooModel(
    override val original: Foo,
    private val adapter: FooAdapter,
) : BaseItemModel<Foo, FooConstraint>() {
    override val constraints = FooConstraint()

    val nameFlow: Flow<String> = itemValueFlow.map { adapter.modelNamePropertyAdapter(it.name) }
    var name: String
        get() = adapter.modelNamePropertyAdapter(itemValue.name)
        set(value) = update { it.copy(name = adapter.entityNamePropertyAdapter(value)) }

}

