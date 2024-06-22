package tech.coner.trailer.toolkit.presentation.model

import assertk.Assert
import assertk.assertions.isInstanceOf
import assertk.assertions.prop
import tech.coner.trailer.toolkit.validation.Feedback

fun <FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Assert<Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>.isEmpty() = isInstanceOf<Loadable.Empty<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>()

fun <FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Assert<Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>.isLoading() = isInstanceOf<Loadable.Loading<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>()
fun <FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Assert<Loadable.Loading<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>.partial() = prop(Loadable.Loading<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>::partial)

fun <FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Assert<Loadable<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>.isLoaded() = isInstanceOf<Loadable.Loaded<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>()
fun <FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>> Assert<Loadable.Loaded<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>>.either() = prop(Loadable.Loaded<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>::either)

// TODO: convenient API for asserting loaded either success or failure
