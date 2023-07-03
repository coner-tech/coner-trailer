package tech.coner.trailer.render.view

import tech.coner.trailer.render.CollectionRenderer

abstract class BaseCollectionViewRenderer<Model>(
    private val lineSeparator: String
) : CollectionRenderer<Model>, ViewRenderer<Model> {

    override fun render(models: Collection<Model>): String {
        return models
            .joinToString(separator = lineSeparator) { render(it) }
    }
}