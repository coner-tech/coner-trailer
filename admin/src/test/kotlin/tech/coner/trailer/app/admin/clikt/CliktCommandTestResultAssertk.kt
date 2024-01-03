package tech.coner.trailer.app.admin.clikt

import assertk.Assert
import assertk.assertions.prop
import com.github.ajalt.clikt.testing.CliktCommandTestResult

fun Assert<CliktCommandTestResult>.statusCode() = prop(CliktCommandTestResult::statusCode)
fun Assert<CliktCommandTestResult>.stderr(trim: Boolean = true) = prop(CliktCommandTestResult::stderr).optionallyTrim(trim)
fun Assert<CliktCommandTestResult>.stdout(trim: Boolean = true) = prop(CliktCommandTestResult::stdout).optionallyTrim(trim)

private fun Assert<String>.optionallyTrim(trim: Boolean) = transform {
    when (trim) {
        true -> it.trim()
        false -> it
    }
}