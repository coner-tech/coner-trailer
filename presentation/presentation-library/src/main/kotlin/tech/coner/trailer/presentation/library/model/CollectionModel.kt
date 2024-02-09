package tech.coner.trailer.presentation.library.model

interface CollectionModel<M : Model> : Model {
    val items: Collection<M>
}
