package org.coner.trailer.render.text

import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.render.EventResultsReportColumnRenderer

abstract class TextResultsReportColumn : EventResultsReportColumnRenderer<
            (ResultsType) -> String,
            (ParticipantResult) -> String
        > {

    class Position : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Pos."
        }
        override val data: (ParticipantResult) -> String = {
            "${it.position}"
        }
    }

    class Signage : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Signage"
        }
        override val data: (ParticipantResult) -> String = {
            render(it.participant.signage)
        }
    }

    class Name : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Name"
        }
        override val data: (ParticipantResult) -> String = {
            renderName(it.participant)
        }
    }

    class Car : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Car"
        }
        override val data: (ParticipantResult) -> String = {
            it.participant.car.model ?: ""
        }
    }

    class Score : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Score"
        }
        override val data: (ParticipantResult) -> String = {
            renderScoreColumnValue(it)
        }
    }

    class DiffFirst : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Diff. - 1st"
        }
        override val data: (ParticipantResult) -> String = {
            render(it.diffFirst)
        }
    }

    class DiffPrevious : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Diff. - Prev."
        }
        override val data: (ParticipantResult) -> String = {
            render(it.diffPrevious)
        }
    }

    class Runs : TextResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Runs"
        }
        override val data: (ParticipantResult) -> String = {
            it.scoredRuns.joinToString(separator = "|") { resultRun -> render(resultRun.run).padEnd(11, '-') }
        }
    }
}
