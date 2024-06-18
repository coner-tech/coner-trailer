package tech.coner.trailer.toolkit.presentation.presenter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import tech.coner.trailer.toolkit.presentation.model.Model
import tech.coner.trailer.toolkit.presentation.state.State

abstract class SecondDraftPresenter<STATE>
        where STATE : State {

    abstract val initialState: STATE
    private val _stateMutex = Mutex()
    private val _stateFlow: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<STATE> by lazy { _stateFlow.asStateFlow() }
    val state: STATE
        get() = runBlocking {
            _stateMutex.withLock {
                _stateFlow.value
            }
        }

    protected suspend fun update(reduceFn: (old: STATE) -> STATE) {
        _stateMutex.withLock {
            _stateFlow.update(reduceFn)
        }
    }

    interface WithArgument<ARGUMENT, ARGUMENT_MODEL : Model> {

        val argument: ARGUMENT
        val argumentModel: ARGUMENT_MODEL

    }
}