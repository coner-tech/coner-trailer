package tech.coner.trailer.toolkit.validation

data class ValidationResult<FEEDBACK : Feedback>(
    val feedback: List<FEEDBACK>
) {
    val isValid: Boolean by lazy {
        feedback.isEmpty()
                || feedback.all { it.severity.valid }
    }

    val isInvalid: Boolean by lazy {
        feedback.isNotEmpty()
                && feedback.any { !it.severity.valid }
    }
}