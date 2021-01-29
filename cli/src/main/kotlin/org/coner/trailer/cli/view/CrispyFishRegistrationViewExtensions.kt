package org.coner.trailer.cli.view

import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.model.Registration

internal fun Registration.renderSignage(): String {
    return renderSignage(
        category = this.category,
        handicap = this.handicap,
        number = this.number
    )
}

internal fun renderSignage(
    category: ClassDefinition?,
    handicap: ClassDefinition,
    number: String
) = when {
    category != null -> "${category.abbreviation} ${handicap.abbreviation} $number"
    else -> "${handicap.abbreviation} $number"
}