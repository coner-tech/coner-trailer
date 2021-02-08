package org.coner.trailer.cli.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.eventresults.*

class OverallResultsReportTableView : View<OverallResultsReport> {

    override fun render(model: OverallResultsReport): String {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Pos.", "Handicap", "#", "Name", "Car Model", model.type.timeColumnHeading)
        at.addRule()
        for (result in model.participantResults) {
            at.addRow(
                result.position,
                result.participant.signage.handicap.abbreviation,
                result.participant.signage.number,
                "${result.participant.firstName} ${result.participant.lastName}",
                result.participant.car.model,
                result.timeColumnValue
            )
            at.addRule()
        }
        return at.render()
    }

    private val ParticipantResult.timeColumnValue: String
        get() = when(score.penalty) {
            Score.Penalty.DidNotFinish -> "DNF"
            Score.Penalty.Disqualified -> "DSQ"
            else -> this.score.value.toString()
        }

    private val ResultsType.timeColumnHeading: String
        get() = when (this) {
            StandardResultsTypes.overallRawTime -> "Raw Time"
            StandardResultsTypes.overallHandicapTime -> "Handicap Time"
            else -> throw UnsupportedOperationException("Cannot render this results type")
        }
}