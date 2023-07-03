package tech.coner.trailer.render.property

import tech.coner.trailer.render.Renderer

/**
 * PropertyRenderer will render a property of a model.
 *
 * The renders from a PropertyRenderer are essentially "partials", suitable for composition into a render from a ViewRenderer.
 *
 * There are two naming conventions that apply to direct subtypes of PropertyRenderer types:
 * 1) {Model}{Property}PropertyRenderer
 *    - These will render the specific named property of the specific named model.
 * 2) {Model}PropertyRenderer
 *    - These will render the specific named model in the "partial" style. These renderer types should only be
 *      implemented for very simplistic model types.
 *
 * @param Model the model whose named property it will render
 */
interface PropertyRenderer<Model> : Renderer<Model>