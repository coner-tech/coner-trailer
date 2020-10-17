package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.client.motorsportreg.model.Member

class MotorsportRegMemberTableView : View<Collection<Member>> {

    override fun render(model: Collection<Member>): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "First", "Last", "Member ID")
        at.addRule()
        model.forEach {
            at.addRow(it.id, it.firstName, it.lastName, it.memberId)
            at.addRule()
        }
        return at.render()
    }
}