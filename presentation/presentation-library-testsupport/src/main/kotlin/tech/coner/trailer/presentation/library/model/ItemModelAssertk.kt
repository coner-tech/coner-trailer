package tech.coner.trailer.presentation.library.model

import assertk.Assert
import assertk.assertions.prop

fun <I> Assert<ItemModel<I>>.item() = prop(ItemModel<I>::item)

fun <I> Assert<ItemModel<I>>.itemValue() = prop(ItemModel<I>::pendingItem)

fun <I> Assert<ItemModel<I>>.isValid() = prop(ItemModel<I>::isValid)

fun <I> Assert<ItemModel<I>>.isDirty() = prop(ItemModel<I>::isDirty)