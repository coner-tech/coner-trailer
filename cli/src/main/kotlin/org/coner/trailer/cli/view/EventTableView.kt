package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event

class EventTableView : View<List<Event>> {

    override fun render(model: List<Event>): String {
        val at = AsciiTable().apply {
            renderer.cwc = CWC_LongestLine()
        }
        at.addRule()
        at.addRow("ID", "Name", "Date", "Crispy Fish")
        at.addRule()
        model.forEach { event ->
            at.addRow(event.id, event.name, event.date, event.crispyFish?.let { "Yes" })
            at.addRule()
        }
        return at.render()
    }
}