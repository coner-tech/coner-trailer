package tech.coner.trailer.toolkit.presentation.adapter

import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.validation.Feedback

abstract class LoadableItemAdapter<ITEM, ITEM_MODEL, FEEDBACK : Feedback>
        where ITEM_MODEL : ItemModel<ITEM, FEEDBACK> {

    abstract val itemToModelAdapter: (ITEM) -> ITEM_MODEL
    abstract val modelToItemAdapter: (ITEM_MODEL) -> ITEM
}

