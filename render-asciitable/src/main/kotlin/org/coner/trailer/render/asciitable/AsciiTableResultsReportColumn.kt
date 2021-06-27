package org.coner.trailer.render.asciitable

import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.render.EventResultsReportColumnRenderer

abstract class AsciiTableResultsReportColumn : EventResultsReportColumnRenderer<
            (ResultsType) -> String,
            (ParticipantResult) -> String
        > {

    class Position : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Pos."
        }
        override val data: (ParticipantResult) -> String = {
            "${it.position}"
        }
    }

    class Signage : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Signage"
        }
        override val data: (ParticipantResult) -> String = {
            render(it.participant.signage)
        }
    }

    class Name : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Name"
        }
        override val data: (ParticipantResult) -> String = {
            renderName(it.participant)
        }
    }

    class Car : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Car"
        }
        override val data: (ParticipantResult) -> String = {
            it.participant.car.model ?: ""
        }
    }

    class Score : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Score"
        }
        override val data: (ParticipantResult) -> String = {
            renderScoreColumnValue(it)
        }
    }

    class DiffFirst : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Diff. - 1st"
        }
        override val data: (ParticipantResult) -> String = {
            render(it.diffFirst)
        }
    }

    class DiffPrevious : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Diff. - Prev."
        }
        override val data: (ParticipantResult) -> String = {
            render(it.diffPrevious)
        }
    }

    class Runs : AsciiTableResultsReportColumn() {
        override val header: (ResultsType) -> String = {
            "Runs"
        }
        override val data: (ParticipantResult) -> String = {
            it.allRuns.joinToString(separator = " ") { run -> render(run) ?: "" }
        }
    }
}
