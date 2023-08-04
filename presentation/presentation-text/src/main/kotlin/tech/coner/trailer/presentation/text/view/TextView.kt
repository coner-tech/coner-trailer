package tech.coner.trailer.presentation.text.view

import tech.coner.trailer.presentation.model.Model
import tech.coner.trailer.presentation.view.View

/**
 * TextView renders the contents of a model for use as a Text UI.
 *
 * A TextView implementation will typically lay out a composition of properties from a model.
 *
 * @param M the type of model whose contents it will render
 */
fun interface TextView<M : Model> : View<M>

