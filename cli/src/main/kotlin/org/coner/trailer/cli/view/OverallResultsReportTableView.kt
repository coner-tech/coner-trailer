package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.eventresults.*
import org.coner.trailer.render.Renderer

class OverallResultsReportTableView : View<OverallResultsReport>, Renderer {

    override fun render(model: OverallResultsReport): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Pos.", "Signage", "Name", "Car Model", model.type.scoreColumnHeading)
        at.addRule()
        for (result in model.participantResults) {
            at.addRow(
                result.position,
                render(result.participant.signage),
                "${result.participant.firstName} ${result.participant.lastName}",
                result.participant.car.model,
                renderScoreColumnValue(result)
            )
            at.addRule()
        }
        return at.render()
    }

}