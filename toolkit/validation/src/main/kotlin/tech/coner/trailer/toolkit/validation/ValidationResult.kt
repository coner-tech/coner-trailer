package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1

data class ValidationResult<FEEDBACK : Feedback>(
    val feedback: Map<KProperty1<*, *>?, List<FEEDBACK>>
) {
    val isValid: Boolean by lazy {
        feedback.isEmpty()
                || feedback.values.all { it.all { feedback -> feedback.severity.valid } }
    }

    val isInvalid: Boolean by lazy {
        feedback.isNotEmpty()
                && feedback.values.any { it.any { feedback -> !feedback.severity.valid } }
    }
}