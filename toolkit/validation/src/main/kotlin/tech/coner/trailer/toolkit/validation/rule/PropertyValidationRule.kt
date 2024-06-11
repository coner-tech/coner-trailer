package tech.coner.trailer.toolkit.validation.rule

import kotlin.reflect.KProperty1
import tech.coner.trailer.toolkit.validation.Feedback

internal interface PropertyValidationRule<CONTEXT, INPUT, PROPERTY, FEEDBACK : Feedback> :
    ValidationRule<CONTEXT, INPUT, FEEDBACK> {
    val property: KProperty1<INPUT, PROPERTY>
}