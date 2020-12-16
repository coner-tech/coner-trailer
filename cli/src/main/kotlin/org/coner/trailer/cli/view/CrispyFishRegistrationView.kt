package org.coner.trailer.cli.view

import org.coner.crispyfish.model.Registration

class CrispyFishRegistrationView : View<Registration> {
    override fun render(model: Registration) = """
        ${model.firstName} ${model.lastName}
        Signage:
            Category:   ${model.category?.abbreviation}
            Handicap:   ${model.handicap.abbreviation}
            Number:     ${model.number}
        Club Member ID: ${model.memberNumber}
    """.trimIndent()
}