package tech.coner.trailer.presentation.library.model

class ModelValidationException(
    val violations: List<Violation>
) : Throwable() {
}