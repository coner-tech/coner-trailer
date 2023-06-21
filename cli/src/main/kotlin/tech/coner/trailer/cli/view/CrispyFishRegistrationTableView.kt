package tech.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.crispyfish.model.Registration

class CrispyFishRegistrationTableView(
    val asciiTableFactory: () -> AsciiTable,
) : View<Collection<Registration>> {

    override fun render(model: Collection<Registration>): String {
        val at = asciiTableFactory()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("First Name", "Last Name", "Signage", "Club Member ID")
        at.addRule()
        for (registration in model) {
            at.addRow(
                registration.firstName ?: "",
                registration.lastName ?: "",
                registration.renderSignage(),
                registration.memberNumber ?: ""
            )
            at.addRule()
        }
        return at.render()
    }
}