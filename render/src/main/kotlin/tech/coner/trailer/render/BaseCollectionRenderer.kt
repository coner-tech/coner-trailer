package tech.coner.trailer.render

abstract class BaseCollectionRenderer<Model>(
    private val lineSeparator: String
) : CollectionRenderer<Model>, Renderer<Model> {

    override fun render(models: Collection<Model>): String {
        return models
            .joinToString(separator = lineSeparator) { render(it) }
    }
}