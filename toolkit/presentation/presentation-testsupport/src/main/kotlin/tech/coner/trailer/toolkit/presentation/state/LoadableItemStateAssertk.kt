package tech.coner.trailer.toolkit.presentation.state

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.presentation.model.ItemModel
import tech.coner.trailer.toolkit.validation.Feedback

fun <STATE : LoadableState<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>, FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>>
        Assert<STATE>.loadable() = prop(LoadableState<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>::loadable)

fun <STATE : LoadableState<FAILURE, ITEM, ITEM_MODEL, FEEDBACK>, FAILURE, ITEM, ITEM_MODEL : ItemModel<ITEM, FEEDBACK>, FEEDBACK : Feedback<ITEM>, PROPERTY>
        Assert<MutableLoadedPropertyDelegate<STATE, FAILURE, ITEM, ITEM_MODEL, FEEDBACK, PROPERTY>>.value()
        = prop(MutableLoadedPropertyDelegate<STATE, FAILURE, ITEM, ITEM_MODEL, FEEDBACK, PROPERTY>::value)