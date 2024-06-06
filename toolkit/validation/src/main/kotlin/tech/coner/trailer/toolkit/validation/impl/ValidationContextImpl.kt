package tech.coner.trailer.toolkit.validation.impl

import tech.coner.trailer.toolkit.validation.ValidationContext
import tech.coner.trailer.toolkit.validation.Feedback

internal class ValidationContextImpl<FEEDBACK : Feedback> : ValidationContext<FEEDBACK> {

    val feedback = mutableListOf<FEEDBACK>()

    override fun give(feedback: FEEDBACK) {
        this.feedback += feedback
    }
}