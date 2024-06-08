package tech.coner.trailer.toolkit.validation.impl

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationContext

internal class ValidationContextImpl<INPUT, FEEDBACK : Feedback>(
    private val property: KProperty1<*, *>? = null,
    private val input: INPUT
) : ValidationContext<INPUT, FEEDBACK> {

    val feedback = mutableMapOf<KProperty1<*, *>?, MutableList<FEEDBACK>>()

    override fun give(feedback: FEEDBACK) {
        this.feedback.createOrAppend(property, feedback)
    }

    override fun <PROPERTY> on(
        property: KProperty1<INPUT, PROPERTY>,
        function: ValidationContext<PROPERTY, FEEDBACK>.(PROPERTY) -> FEEDBACK?
    ) {
        val propertyValue = property.get(input)
        val propertyContext = ValidationContextImpl<PROPERTY, FEEDBACK>(property, propertyValue)
        function(propertyContext, propertyValue)
            ?.also { propertyContext.give(it) }
        feedback.createOrAppend(propertyContext.feedback)
    }

    override fun <PROPERTY> on(
        property: KProperty1<INPUT, PROPERTY>,
        vararg functions: ValidationContext<PROPERTY, FEEDBACK>.(PROPERTY) -> FEEDBACK?
    ) {
        val propertyValue = property.get(input)
        val propertyContext = ValidationContextImpl<PROPERTY, FEEDBACK>(property, propertyValue)
        functions.forEach { function ->
            function(propertyContext, propertyValue)
                ?.also { propertyContext.give(it) }
        }
        feedback.createOrAppend(propertyContext.feedback)
    }
}