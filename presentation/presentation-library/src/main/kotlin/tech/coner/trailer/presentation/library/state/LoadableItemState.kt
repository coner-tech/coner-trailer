package tech.coner.trailer.presentation.library.state

import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.model.LoadableModel

data class LoadableItemState<ITEM, ITEM_MODEL : ItemModel<ITEM>>(
    val loadable: LoadableModel<ITEM, ITEM_MODEL>
) : State
