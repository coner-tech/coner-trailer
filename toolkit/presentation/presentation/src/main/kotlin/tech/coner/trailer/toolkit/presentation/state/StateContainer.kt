package tech.coner.trailer.toolkit.presentation.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import tech.coner.trailer.toolkit.presentation.state.StateContainer.ReducedStateInspector

class StateContainer<STATE : State>(
    val initialState: STATE,
    private val isReducedStateLegal: ReducedStateInspector<STATE> = ReducedStateInspector { _, _ -> true }
) {

    private val _stateMutex = Mutex()
    private val _stateFlow: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<STATE> by lazy { _stateFlow.asStateFlow() }
    val state: STATE
        get() = runBlocking {
            _stateMutex.withLock {
                _stateFlow.value
            }
        }

    suspend fun update(reduceFn: (old: STATE) -> STATE) {
        _stateMutex.withLock {
            val oldState = _stateFlow.value
            val reducedState = reduceFn(oldState)
            check(isReducedStateLegal(oldState = oldState, reducedState = reducedState))
            _stateFlow.emit(reducedState)
        }
    }

    /**
     * Inspects reduced state for legality.
     *
     * StateContainer will invoke this interface with the old and reduced state when updating to check it for legality.
     *
     * Use this as a last-ditch safety measure to prevent your app entering an illegal state. Be prepared to handle
     * the IllegalStateException or else your app may crash.
     *
     * This is not the place to perform expensive validations. This will be invoked on every call to
     * `StateContainer.update` with a mutex lock. Keep it simple, light, fast, and safe.
     */
    fun interface ReducedStateInspector<STATE : State> {

        operator fun invoke(oldState: STATE, reducedState: STATE): Boolean
    }

    open inner class StatePropertyContainer<PROPERTY>(
        private val getValue: STATE.() -> PROPERTY
    ) {
        val immutableValue: PROPERTY
            get() = state.getValue()
        val flow: Flow<PROPERTY> = stateFlow.map(getValue)
    }

    fun <PROPERTY> stateProperty(
        getValue: STATE.() -> PROPERTY
    ): StatePropertyContainer<PROPERTY> {
        return StatePropertyContainer(getValue)
    }


    inner class MutableStatePropertyContainer<PROPERTY>(
        getValue: STATE.() -> PROPERTY,
        private val updateState: STATE.(PROPERTY) -> STATE
    ) : StatePropertyContainer<PROPERTY>(getValue) {

        var value: PROPERTY
            get() = immutableValue
            set(value) { runBlocking { update { it.updateState(value) } } }
    }

    fun <PROPERTY> mutableStateProperty(
        getValue: STATE.() -> PROPERTY,
        updateState: STATE.(newValue: PROPERTY) -> STATE
    ): MutableStatePropertyContainer<PROPERTY> {
        return MutableStatePropertyContainer(getValue, updateState)
    }
}