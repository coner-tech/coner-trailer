package tech.coner.trailer.presentation.library.testsupport.fooapp.presentation.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.constraint.FooConstraint
import tech.coner.trailer.presentation.library.testsupport.fooapp.domain.entity.Foo

class FooModel(override val original: Foo) : BaseItemModel<Foo, FooConstraint>() {
    override val constraints = FooConstraint()

    val nameFlow: Flow<String> = itemValueFlow.map {
        it.name.capitalizeFirstChar()
    }

    fun setName(name: String) {
        updateItem { it.copy(name = name) }
    }

    private fun String.capitalizeFirstChar(): String {
        return when (length) {
            0 -> this
            1 -> uppercase()
            else -> let { "${it[0].uppercaseChar()}${it.substring(1)}" }
        }
    }
}

