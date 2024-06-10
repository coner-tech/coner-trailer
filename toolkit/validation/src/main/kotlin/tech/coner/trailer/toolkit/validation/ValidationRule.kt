package tech.coner.trailer.toolkit.validation

fun interface ValidationRule<INPUT, FEEDBACK : Feedback> {

    operator fun invoke(input: INPUT): FEEDBACK?
}