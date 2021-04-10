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
        at.addRow("ID", "Name", "Date", "Policy", "Crispy Fish", "MotorsportReg")
        at.addRule()
        model.forEach { event ->
            at.addRow(event.id, event.name, event.date, event.policy.name, yesOrEmpty(event.crispyFish), yesOrEmpty(event.motorsportReg))
            at.addRule()
        }
        return at.render()
    }

    private fun yesOrEmpty(ref: Any?) = ref?.let { "Yes" } ?: ""
}