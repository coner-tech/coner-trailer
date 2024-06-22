package tech.coner.trailer.toolkit.presentation.model

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.validation.Feedback

fun <I, FEEDBACK : Feedback<I>> Assert<ItemModel<I, FEEDBACK>>.item() = prop(ItemModel<I, FEEDBACK>::item)

fun <I, FEEDBACK : Feedback<I>> Assert<ItemModel<I, FEEDBACK>>.pendingItem() = prop(ItemModel<I, FEEDBACK>::pendingItem)

fun <I, FEEDBACK : Feedback<I>> Assert<ItemModel<I, FEEDBACK>>.pendingItemValidation() = prop(ItemModel<I, FEEDBACK>::pendingItemValidation)

fun <I, FEEDBACK : Feedback<I>> Assert<ItemModel<I, FEEDBACK>>.isValid() = prop(ItemModel<I, FEEDBACK>::isPendingItemValid)

fun <I, FEEDBACK : Feedback<I>> Assert<ItemModel<I, FEEDBACK>>.isDirty() = prop(ItemModel<I, FEEDBACK>::isPendingItemDirty)