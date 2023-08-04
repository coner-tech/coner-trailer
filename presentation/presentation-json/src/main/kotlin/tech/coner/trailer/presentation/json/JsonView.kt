package tech.coner.trailer.presentation.json

import tech.coner.trailer.presentation.model.Model
import tech.coner.trailer.presentation.view.View

fun interface JsonView<M : Model> : View<M>
