package tech.coner.trailer.presentation.text.view

import tech.coner.trailer.presentation.model.CollectionModel
import tech.coner.trailer.presentation.model.Model
import tech.coner.trailer.presentation.view.View

fun interface TextCollectionView<M : Model, CM : CollectionModel<M>> : View<CM>

