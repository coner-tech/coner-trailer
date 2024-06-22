package tech.coner.trailer.toolkit

sealed interface Error {
    data object NotFound : Error
    data object AlreadyExists : Error
    data object ConcurrentEntry : Error
    data object InvalidState : Error

    /**
     * Indicates unhandled invalid input.
     *
     * Toolkit users should take care to handle MutationOutcome.InvalidInputFailure and make its ValidationResult's
     * feedback available to the user in context.
     */
    data object InvalidInput : Error
    data object Timeout : Error

    // expand as necessary

    data class Exceptional(val throwable: Throwable) : Error

    fun toException() = ErrorException(
        error = this,
        cause = when (this) {
            is Exceptional -> throwable
            else -> null
        }
    )
}