package tech.coner.trailer.app.admin.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Event

class PeopleMapKeyTableView(private val asciiTableFactory: () -> AsciiTable) : View<Collection<Event.CrispyFishMetadata.PeopleMapKey>> {

    override fun render(model: Collection<Event.CrispyFishMetadata.PeopleMapKey>): String {
        val at = asciiTableFactory()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Signage", "First Name", "Last Name")
        at.addRule()
        for (key in model) {
            at.addRow("${key.classing.group?.abbreviation} ${key.classing.handicap.abbreviation} ${key.number}".trim(), key.firstName, key.lastName)
            at.addRule()
        }
        return at.render()
    }
}