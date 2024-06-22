package tech.coner.trailer.toolkit.presentation.state

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.validation.Feedback

fun <ITEM, FEEDBACK : Feedback<ITEM>> Assert<ItemModelState<ITEM, FEEDBACK>>.itemModel() = prop(ItemModelState<ITEM, FEEDBACK>::itemModel)