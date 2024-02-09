package tech.coner.trailer.presentation.library.view

import tech.coner.trailer.presentation.library.model.Model

fun interface View<M : Model> {
    operator fun invoke(model: M): String
}