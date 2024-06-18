package tech.coner.trailer.toolkit.presentation.model

import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationResult

sealed class CommitOutcome<ITEM, FEEDBACK : Feedback> {

    abstract val item: ITEM
    abstract val feedback: ValidationResult<ITEM, FEEDBACK>?

    data class Success<ITEM, FEEDBACK : Feedback>(
        override val item: ITEM,
        override val feedback: ValidationResult<ITEM, FEEDBACK>?
    ) : CommitOutcome<ITEM, FEEDBACK>()

    data class Failure<ITEM, FEEDBACK : Feedback>(
        override val item: ITEM,
        override val feedback: ValidationResult<ITEM, FEEDBACK>
    ) : CommitOutcome<ITEM, FEEDBACK>()

    fun onSuccess(fn: (Success<ITEM, FEEDBACK>) -> Unit): CommitOutcome<ITEM, FEEDBACK> {
        if (this is Success<ITEM, FEEDBACK>) fn(this)
        return this
    }

    fun onFailure(fn: (Failure<ITEM, FEEDBACK>) -> Unit): CommitOutcome<ITEM, FEEDBACK> {
        if (this is Failure<ITEM, FEEDBACK>) fn(this)
        return this
    }
}