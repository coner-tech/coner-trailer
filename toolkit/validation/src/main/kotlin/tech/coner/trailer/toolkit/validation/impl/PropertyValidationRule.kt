package tech.coner.trailer.toolkit.validation.impl

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.ValidationRule

internal interface PropertyValidationRule<INPUT, PROPERTY, FEEDBACK : Feedback> : ValidationRule<INPUT, FEEDBACK> {
    val property: KProperty1<INPUT, PROPERTY>
}