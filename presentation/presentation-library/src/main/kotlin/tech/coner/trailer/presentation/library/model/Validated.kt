package tech.coner.trailer.presentation.library.model

class Validated<V>(val value: V, val violations: List<Violation>) {

    val isValid: Boolean
        get() = violations.isEmpty()
}
