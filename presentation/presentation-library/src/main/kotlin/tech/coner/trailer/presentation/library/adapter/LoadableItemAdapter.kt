package tech.coner.trailer.presentation.library.adapter

import tech.coner.trailer.presentation.library.model.ItemModel

abstract class LoadableItemAdapter<ARGUMENT, ITEM, ARGUMENT_MODEL, ITEM_MODEL>
        where ITEM_MODEL : ItemModel<ITEM> {

    abstract val argumentToModelAdapter: ((ARGUMENT) -> ARGUMENT_MODEL)?
    abstract val itemToModelAdapter: (ITEM) -> ITEM_MODEL
    abstract val modelToItemAdapter: (ITEM_MODEL) -> ITEM
}

