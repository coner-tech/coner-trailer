package org.coner.trailer.cli.view

import org.coner.crispyfish.model.Registration

class CrispyFishRegistrationView : View<Registration> {
    override fun render(model: Registration) = """
        Name:           ${model.firstName} ${model.lastName}
        Signage:        ${model.renderSignage()}
        Club Member ID: ${model.memberNumber}
    """.trimIndent()

}