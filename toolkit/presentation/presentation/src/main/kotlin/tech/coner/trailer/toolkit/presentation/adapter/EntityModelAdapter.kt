package tech.coner.trailer.toolkit.presentation.adapter

abstract class EntityModelAdapter<ITEM, MODEL>  {

    abstract val entityToModelAdapter: (ITEM) -> MODEL
    abstract val modelToEntityAdapter: (MODEL) -> ITEM
}

