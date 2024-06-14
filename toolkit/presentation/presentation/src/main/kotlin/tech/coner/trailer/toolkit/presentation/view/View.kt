package tech.coner.trailer.toolkit.presentation.view

import tech.coner.trailer.toolkit.presentation.model.Model

fun interface View<M : Model> {
    operator fun invoke(model: M): String
}