package tech.coner.trailer.presentation.text.view

import tech.coner.trailer.presentation.model.Model

/**
 * A portion of a view representing the substance of a model, but omitting any extra fluff like headers, footers, etc.
 * The content of the partial should be suitable to include into a composite view.
 * @param R The type of receiver this partial can append with its representation of the model
 * @param M The type of model
 */
interface TextPartial<R, M : Model> {

    /**
     * Append the portion of content that represents the substance of the model, omitting any extra fluff like headers, footers, etc..
     * This method is intended to be called within a receiver scope for a full view.
     * @param model The model to render
     */
    fun R.appendModel(model: M)
}