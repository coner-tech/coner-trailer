package tech.coner.trailer.presentation.view

import tech.coner.trailer.presentation.model.Model

fun interface View<M : Model> {
    operator fun invoke(model: M): String
}