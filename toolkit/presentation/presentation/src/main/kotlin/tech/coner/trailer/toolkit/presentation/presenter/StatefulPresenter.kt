package tech.coner.trailer.toolkit.presentation.presenter

import kotlinx.coroutines.flow.StateFlow

interface StatefulPresenter<S> {

    val state: S
    val stateFlow: StateFlow<S>
}