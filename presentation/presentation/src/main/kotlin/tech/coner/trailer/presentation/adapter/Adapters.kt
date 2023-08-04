package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.presentation.model.Model

/**
 * Adapter will convert some or all of an input model into a presentable Output model.
 *
 * The Model values returned by Adapters are suitable for composition into a View.
 *
 * @param InputModel the type of model used as the parameter
 * @param OutputModel the presentable model returned
 */
fun interface Adapter<InputModel, OutputModel : Model> {
    operator fun invoke(model: InputModel): OutputModel
}

fun interface ListAdapter<InputModel, OutputModel : Model> {
    operator fun invoke(model: InputModel): List<OutputModel>
}

/**
 *
 */
fun interface StringFieldAdapter<InputModel> {
    operator fun invoke(model: InputModel): String
}