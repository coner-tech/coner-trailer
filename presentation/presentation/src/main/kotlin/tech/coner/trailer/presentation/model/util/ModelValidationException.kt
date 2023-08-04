package tech.coner.trailer.presentation.model.util

class ModelValidationException(
    val violations: List<Violation>
) : Throwable() {
}