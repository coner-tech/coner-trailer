package tech.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Person

class PersonTableView(
    private val asciiTableFactory: () -> AsciiTable,
) : View<List<Person>> {
    override fun render(model: List<Person>): String {
        val at = asciiTableFactory()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "First", "Last", "Member ID", "MSR ID")
        at.addRule()
        model.forEach {
            at.addRow(it.id, it.firstName, it.lastName, it.clubMemberId, it.motorsportReg?.memberId)
            at.addRule()
        }
        return at.render()
    }

}
