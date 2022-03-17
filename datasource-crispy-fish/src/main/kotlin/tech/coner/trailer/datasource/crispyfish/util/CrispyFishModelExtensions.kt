package tech.coner.trailer.datasource.crispyfish.util

import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.StagingLineRegistration

fun Registration.syntheticSignageKey(): String? {
    val category = category
    val handicap = handicap
    val number = number
    return when {
        category != null && handicap != null && number != null -> "${category.abbreviation}${handicap.abbreviation} $number"
        handicap != null && number != null -> "${handicap.abbreviation} $number"
        number != null -> number
        else -> null
    }
}
fun StagingLineRegistration.syntheticSignageKey() = "$classing $number"