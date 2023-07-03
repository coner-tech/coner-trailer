package tech.coner.trailer.render.view

import tech.coner.trailer.render.Renderer

/**
 * ViewRenderer can render the contents of a model for use as a view.
 *
 * Think of a view as a composition of rendered properties in a model.
 *
 * @param Model the type of model whose contents it will render
 */
interface ViewRenderer<Model> : Renderer<Model>
