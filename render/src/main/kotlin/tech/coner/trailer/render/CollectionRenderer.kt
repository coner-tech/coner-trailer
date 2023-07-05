package tech.coner.trailer.render

/**
 * A render
 */
interface CollectionRenderer<Model> {

    fun render(models: Collection<Model>): String

    operator fun invoke(models: Collection<Model>) = render(models)
}