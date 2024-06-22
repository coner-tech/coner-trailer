package tech.coner.trailer.toolkit.validation

import tech.coner.trailer.toolkit.validation.adapter.PropertyAdapter
import kotlin.reflect.KProperty1

class FeedbackDelegate<INPUT, WRAPPER>(
    val feedback: Feedback<INPUT>,
    val propertyAdapter: PropertyAdapter<INPUT, WRAPPER>
) : Feedback<WRAPPER> {
    override val property: KProperty1<WRAPPER, *>?
        get() = propertyAdapter(feedback.property)
    override val severity: Severity
        get() = feedback.severity
}
