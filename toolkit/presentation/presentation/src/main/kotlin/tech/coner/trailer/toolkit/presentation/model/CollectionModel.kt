package tech.coner.trailer.toolkit.presentation.model

interface CollectionModel<M : Model> : Model {
    val items: Collection<M>
}
