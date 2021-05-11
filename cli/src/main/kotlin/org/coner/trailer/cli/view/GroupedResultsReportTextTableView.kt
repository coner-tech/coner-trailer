package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.render.Renderer

class GroupedResultsReportTextTableView : View<GroupedResultsReport>, Renderer {

    override fun render(model: GroupedResultsReport): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        model.groupingsToResultsMap.forEach { (grouping, participantResults) ->
            at.addRule()
            at.addRow(null, null, null, null, grouping.name)
            at.addRule()
            at.addRow("Pos.", "Signage", "Name", "Car Model", model.type.scoreColumnHeading)
            at.addRule()
            for (participantResult in participantResults) {
                at.addRow(
                    participantResult.position,
                    render(participantResult.participant.signage),
                    "${participantResult.participant.firstName} ${participantResult.participant.lastName}",
                    participantResult.participant.car.model,
                    renderScoreColumnValue(participantResult)
                )
            }
            at.addRule()
        }
        return at.render()
    }
}