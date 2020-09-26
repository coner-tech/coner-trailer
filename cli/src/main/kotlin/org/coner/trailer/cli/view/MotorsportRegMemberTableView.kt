package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.client.motorsportreg.model.GetMembersResponse

class MotorsportRegMemberTableView : View<Collection<GetMembersResponse.Member>> {

    override fun render(model: Collection<GetMembersResponse.Member>): String {
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