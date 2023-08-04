package tech.coner.trailer.presentation.model

interface CollectionModel<M : Model> : Model {
    val items: Collection<M>
}
