package tech.coner.trailer.render

/**
 * Interface for rendering a specific type of Model
 * @param Model the model type handled by this Renderer
 */
fun interface Renderer<Model> {

    /**
     * Render a model
     * @param model the model to render
     * @return a string representing the model
     */
    fun render(model: Model): String

    /**
     * Functional-style convenience to render a model
     * @param model the model to render
     * @return a string representing the model
     */
    operator fun invoke(model: Model): String = render(model)
}
