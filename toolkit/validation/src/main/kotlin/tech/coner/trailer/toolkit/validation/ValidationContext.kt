package tech.coner.trailer.toolkit.validation

interface ValidationContext<FEEDBACK : Feedback> {

    fun give(feedback: FEEDBACK)
}