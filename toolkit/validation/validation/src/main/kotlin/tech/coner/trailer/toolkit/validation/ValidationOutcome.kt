package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1

data class ValidationOutcome<INPUT, FEEDBACK : Feedback<INPUT>>(
    val feedback: List<FEEDBACK>
) {

    val feedbackByProperty: Map<KProperty1<INPUT, *>?, List<FEEDBACK>> by lazy {
        feedback.groupBy { it.property }
    }

    val isValid: Boolean by lazy {
        feedback.isEmpty()
                || feedback.isValid
    }

    val isInvalid: Boolean by lazy {
        feedback.isNotEmpty()
                && feedback.isInvalid
    }

    /**
     * When this result is valid, invoke `validFn` or no-op
     *
     * @param validFn the function to invoke if this result is valid
     * @return this, fluent interface
     */
    fun whenValid(validFn: () -> Unit): ValidationOutcome<INPUT, FEEDBACK> {
        if (isValid) validFn()
        return this
    }

    /**
     * When this result is valid, invoke `validFn` and return its result or no-op and return null
     *
     * @param validFn the function to invoke if this result is valid
     * @return the result of validFn,
     */
    fun <R> letValid(validFn: () -> R): R? {
        return if (isValid) validFn() else null
    }
}

val <INPUT> List<Feedback<INPUT>>.isValid: Boolean
    get() = all { feedback -> feedback.severity.valid }

val <INPUT> List<Feedback<INPUT>>.isInvalid: Boolean
    get() = any { feedback -> !feedback.severity.valid }