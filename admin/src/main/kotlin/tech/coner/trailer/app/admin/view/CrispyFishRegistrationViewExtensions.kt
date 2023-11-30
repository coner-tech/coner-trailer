package tech.coner.trailer.app.admin.view

import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.Registration

internal fun Registration.renderSignage(): String {
    return renderSignage(
        category = this.signage.classing?.category,
        handicap = this.signage.classing?.handicap,
        number = this.signage.number
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