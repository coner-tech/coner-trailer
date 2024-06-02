package tech.coner.trailer.presentation.library.model

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.prop

fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel<ITEM, ITEM_MODEL>>.isEmpty() = isInstanceOf<LoadableModel.Empty<ITEM, ITEM_MODEL>>()

fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel<ITEM, ITEM_MODEL>>.isLoading() = isInstanceOf<LoadableModel.Loading<ITEM, ITEM_MODEL>>()
fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel.Loading<ITEM, ITEM_MODEL>>.partial() = prop(LoadableModel.Loading<ITEM, ITEM_MODEL>::partial)

fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel<ITEM, ITEM_MODEL>>.isLoaded() = isInstanceOf<LoadableModel.Loaded<ITEM, ITEM_MODEL>>()
fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel.Loaded<ITEM, ITEM_MODEL>>.item() = prop(LoadableModel.Loaded<ITEM, ITEM_MODEL>::item)

fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel<ITEM, ITEM_MODEL>>.isLoadFailed() = isInstanceOf<LoadableModel.LoadFailed<ITEM, ITEM_MODEL>>()
fun <ITEM, ITEM_MODEL : ItemModel<ITEM>> Assert<LoadableModel.LoadFailed<ITEM, ITEM_MODEL>>.cause() = prop(LoadableModel.LoadFailed<ITEM, ITEM_MODEL>::cause)






