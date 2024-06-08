package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1

interface ValidationContext<INPUT, FEEDBACK : Feedback> {

    fun give(feedback: FEEDBACK)

    fun <PROPERTY> on(
        property: KProperty1<INPUT, PROPERTY>,
        function: ValidationContext<PROPERTY, FEEDBACK>.(PROPERTY) -> FEEDBACK?
    )

    fun <PROPERTY> on(
        property: KProperty1<INPUT, PROPERTY>,
        vararg functions: ValidationContext<PROPERTY, FEEDBACK>.(PROPERTY) -> FEEDBACK?
    )
}