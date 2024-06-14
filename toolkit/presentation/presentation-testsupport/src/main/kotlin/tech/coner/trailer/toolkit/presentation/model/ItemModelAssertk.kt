package tech.coner.trailer.toolkit.presentation.model

import assertk.Assert
import assertk.assertions.prop

fun <I> Assert<ItemModel<I>>.item() = prop(ItemModel<I>::item)

fun <I> Assert<ItemModel<I>>.pendingItem() = prop(ItemModel<I>::pendingItem)

fun <I> Assert<ItemModel<I>>.violations() = prop(ItemModel<I>::pendingItemValidation)

fun <I> Assert<ItemModel<I>>.isValid() = prop(ItemModel<I>::isPendingItemValid)

fun <I> Assert<ItemModel<I>>.isDirty() = prop(ItemModel<I>::isPendingItemDirty)