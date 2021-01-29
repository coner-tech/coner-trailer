package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event

class PeopleMapKeyTableView : View<Collection<Event.CrispyFishMetadata.PeopleMapKey>> {

    override fun render(model: Collection<Event.CrispyFishMetadata.PeopleMapKey>): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Signage", "First Name", "Last Name")
        at.addRule()
        for (key in model) {
            at.addRow(key.signage.grouping.abbreviation, key.firstName, key.lastName)
            at.addRule()
        }
        return at.render()
    }
}