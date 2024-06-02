package tech.coner.trailer.presentation.library.state

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.presentation.library.model.ItemModel

fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableItemState<ITEM, ITEM_MODEL>>.loadable() = prop(LoadableItemState<ITEM, ITEM_MODEL>::loadable)