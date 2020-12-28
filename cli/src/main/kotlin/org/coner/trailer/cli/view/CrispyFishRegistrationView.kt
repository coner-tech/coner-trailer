package org.coner.trailer.cli.view

import org.coner.crispyfish.model.Registration

class CrispyFishRegistrationView : View<Registration> {
    override fun render(model: Registration) = """
        Name:           ${model.firstName} ${model.lastName}
        Signage:        ${renderSignage(model)}
        Club Member ID: ${model.memberNumber}
    """.trimIndent()

    private fun renderSignage(model: Registration): String {
        val category = model.category
        val handicap = model.handicap
        val number = model.number
        return when {
            category != null -> "${category.abbreviation} ${handicap.abbreviation} $number"
            else -> "${handicap.abbreviation} $number"
        }
    }
}