package tech.coner.trailer.render.text

import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.EventResultsColumnRenderer

abstract class TextEventResultsColumn : EventResultsColumnRenderer<
            (EventResultsType) -> String,
            (ParticipantResult) -> String
        > {

    class Position : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Pos."
        }
        override val data: (ParticipantResult) -> String = {
            "${it.position}"
        }
    }

    class Signage : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Signage"
        }
        override val data: (ParticipantResult) -> String = {
            it.participant.signageClassingNumber ?: ""
        }
    }

    class Name : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Name"
        }
        override val data: (ParticipantResult) -> String = {
            renderName(it.participant)
        }
    }

    class Car : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Car"
        }
        override val data: (ParticipantResult) -> String = {
            it.participant.car.model ?: ""
        }
    }

    class Score : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Score"
        }
        override val data: (ParticipantResult) -> String = {
            renderScoreColumnValue(it)
        }
    }

    class DiffFirst : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Diff. - 1st"
        }
        override val data: (ParticipantResult) -> String = {
            render(it.diffFirst)
        }
    }

    class DiffPrevious : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Diff. - Prev."
        }
        override val data: (ParticipantResult) -> String = {
            render(it.diffPrevious)
        }
    }

    class Runs : TextEventResultsColumn() {
        override val header: (EventResultsType) -> String = {
            "Runs"
        }
        override val data: (ParticipantResult) -> String = { participantResult ->
            val bestScoredRun = participantResult.personalBestScoredRunIndex?.let { participantResult.scoredRuns[it] }
            participantResult.scoredRuns.joinToString(separator = "|") { resultRun ->
                render(resultRun.run)
                    .padEnd(12, if (resultRun !== bestScoredRun) '-' else '*')
            }
        }
    }
}
