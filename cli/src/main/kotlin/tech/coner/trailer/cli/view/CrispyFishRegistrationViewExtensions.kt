package tech.coner.trailer.cli.view

import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.Registration

internal fun Registration.renderSignage(): String {
    return renderSignage(
        category = this.category,
        handicap = this.handicap,
        number = this.number
    )
}

internal fun renderSignage(
    category: ClassDefinition?,
    handicap: ClassDefinition?,
    number: String?
): String {
    val categoryAbbreviation = category?.abbreviation ?: ""
    val handicapAbbreviation = handicap?.abbreviation ?: ""
    return "$categoryAbbreviation $handicapAbbreviation $number".trim()
}