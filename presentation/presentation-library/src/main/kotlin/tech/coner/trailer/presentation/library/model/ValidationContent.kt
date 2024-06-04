package tech.coner.trailer.presentation.library.model

sealed class ValidationContent {
    abstract val valid: Boolean
    abstract val message: String

    data class Error(override val message: String) : ValidationContent() {
        override val valid: Boolean = false
    }

    sealed class ValidValidationContent : ValidationContent() {
        override val valid: Boolean = true
    }

    data class Warning(override val message: String) : ValidValidationContent()
    data class Success(override val message: String) : ValidValidationContent()
    data class Info(override val message: String) : ValidValidationContent()

}

fun List<ValidationContent>.isValid(): Boolean {
    return isEmpty()
            || all { it.valid }
}

fun List<ValidationContent>.isInvalid(): Boolean {
    return isNotEmpty() && any { !it.valid }
}