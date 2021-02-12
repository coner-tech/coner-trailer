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
        at.addRow("Pos.", "Handicap", "#", "Name", "Car Model", model.type.scoreColumnHeading)
        at.addRule()
        for (result in model.participantResults) {
            at.addRow(
                result.position,
                result.participant.signage.handicap.abbreviation,
                result.participant.signage.number,
                "${result.participant.firstName} ${result.participant.lastName}",
                result.participant.car.model,
                renderScoreColumnValue(result)
            )
            at.addRule()
        }
        return at.render()
    }

}