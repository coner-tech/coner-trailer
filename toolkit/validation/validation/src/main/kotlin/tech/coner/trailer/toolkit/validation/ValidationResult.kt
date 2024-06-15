package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1

data class ValidationResult<INPUT, FEEDBACK : Feedback>(
    val feedback: Map<KProperty1<INPUT, *>?, List<FEEDBACK>>
) {
    val isValid: Boolean by lazy {
        feedback.isEmpty()
                || feedback.values.all { it.all { feedback -> feedback.severity.valid } }
    }

    val isInvalid: Boolean by lazy {
        feedback.isNotEmpty()
                && feedback.values.any { it.any { feedback -> !feedback.severity.valid } }
    }

    /**
     * When this result is valid, invoke `validFn` and return its result, or `null` otherwise
     *
     * @param validFn the function to invoke if this result is valid
     * @return the result of `validFn` if this result is valid, or `null` otherwise
     */
    fun <R> whenValid(validFn: () -> R?): R? {
        return when {
            isValid -> validFn()
            else -> null
        }
    }
}