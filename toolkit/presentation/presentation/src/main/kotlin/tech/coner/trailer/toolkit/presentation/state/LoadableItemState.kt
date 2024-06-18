package tech.coner.trailer.toolkit.presentation.state

import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.presentation.model.LoadableModel
import tech.coner.trailer.toolkit.validation.Feedback

data class LoadableItemState<ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback>(
    val loadable: LoadableModel<ITEM, ITEM_MODEL, FEEDBACK>
) : State
