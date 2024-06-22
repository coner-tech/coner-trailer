package tech.coner.trailer.toolkit.presentation.state

import assertk.Assert
import assertk.assertions.prop
import tech.coner.trailer.toolkit.presentation.presenter.StatefulPresenter

fun <S> Assert<StatefulPresenter<S>>.state() = prop(StatefulPresenter<S>::state)