package tech.coner.trailer.toolkit.presentation.model

class Validated<V>(val value: V, val violations: List<Violation>) {

    val isValid: Boolean
        get() = violations.isEmpty()
}
