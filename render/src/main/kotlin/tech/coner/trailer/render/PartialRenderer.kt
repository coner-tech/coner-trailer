package tech.coner.trailer.render

/**
 * A renderer for a partial representing the core content, omitting any extra fluff like headers, footers, etc.
 * @param Model The type of model to render
 * @param PartialReceiver The receiver type to attach the extension function. This should be some type of builder-style DSL like StringBuilder, etc
 */
interface PartialRenderer<Model, PartialReceiver> {

    /**
     * Render a partial for the model. This method is intended to be called within a receiver scope for a full render, such as in a builder-style DSL like StringBuilder, etc.
     *
     * This render will omit any extra surrounding fluff like headers, footers, etc.
     * @param model The model to render
     */
    fun PartialReceiver.renderPartial(model: Model)
}