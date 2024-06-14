package tech.coner.trailer.toolkit.presentation.model

class ModelValidationException(
    val violations: List<Violation>
) : Throwable() {
}