package tech.coner.trailer.toolkit.presentation.state

import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.presentation.model.LoadableModel

data class LoadableItemState<ITEM, ITEM_MODEL : ItemModel<ITEM>>(
    val loadable: LoadableModel<ITEM, ITEM_MODEL>
) : State
