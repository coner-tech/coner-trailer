package tech.coner.trailer.presentation.text.view

import tech.coner.trailer.presentation.model.CollectionModel
import tech.coner.trailer.presentation.model.Model

class SimpleTextCollectionView<M : Model, CM : CollectionModel<M>>(
    private val view: TextView<M>,
    private val lineSeparator: String
) : TextCollectionView<M, CM> {

    override fun invoke(model: CM): String {
        return model.items
            .joinToString(
                separator = "$lineSeparator$lineSeparator",
                transform = view::invoke
            )
    }
}