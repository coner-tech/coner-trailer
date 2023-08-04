package tech.coner.trailer.presentation.model.util

class Validated<V>(val value: V, val violations: List<Violation>) {

    val isValid: Boolean
        get() = violations.isEmpty()
}
