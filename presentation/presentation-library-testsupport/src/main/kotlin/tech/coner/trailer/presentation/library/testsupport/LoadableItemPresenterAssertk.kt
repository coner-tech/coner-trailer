package tech.coner.trailer.presentation.library.testsupport

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.presentation.library.model.ItemModel
import tech.coner.trailer.presentation.library.presenter.LoadableItemPresenter
import tech.coner.trailer.presentation.library.state.LoadableItemState


fun <S : LoadableItemState<I>, I, IM : ItemModel<I>> Assert<LoadableItemPresenter<S, I, IM>>.itemModelFlow() = prop(LoadableItemPresenter<S, I, IM>::itemModelFlow)
fun <S : LoadableItemState<I>, I, IM : ItemModel<I>> Assert<LoadableItemPresenter<S, I, IM>>.itemModel() = prop(LoadableItemPresenter<S, I, IM>::itemModel)