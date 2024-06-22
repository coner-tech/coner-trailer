package tech.coner.trailer.toolkit.validation

import kotlin.reflect.KProperty1

interface Feedback<INPUT> {
    val property: KProperty1<INPUT, *>?
    val severity: Severity
}
