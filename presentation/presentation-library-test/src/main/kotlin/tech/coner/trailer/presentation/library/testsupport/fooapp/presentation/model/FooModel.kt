package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint.FooConstraint
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo

class FooModel(override val original: Foo) : BaseItemModel<Foo, FooConstraint>() {
    override val constraints = FooConstraint()

    val nameFlow: Flow<String> = itemValueFlow.map { /* TODO: adapter */ nameFn(it) }
    var name: String
        get() = /* TODO: adapter */ nameFn(itemValue)
        set(value) = updateItem { /* TODO: adapter */ it.copy(name = value.lowercase()) }
    private val nameFn: (Foo) -> String = { /* TODO: adapter */ name.capitalizeFirstChar() }


}

