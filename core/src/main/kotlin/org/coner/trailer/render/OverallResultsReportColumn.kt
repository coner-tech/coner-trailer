package org.coner.trailer.render

import kotlinx.html.*
import org.coner.trailer.eventresults.ParticipantResult

interface OverallResultsReportColumn : Renderer {

    val header: TR.() -> Unit

    val data: TR.(ParticipantResult) -> Unit

    class Position : OverallResultsReportColumn {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("position")
                scope = ThScope.col
                text("Pos.")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            th {
                scope = ThScope.row
                text(it.position)
            }
        }
    }

    class Signage : OverallResultsReportColumn {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("signage")
                scope = ThScope.col
                text("Signage")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(render(it.participant.signage)) }
        }
    }

    class Name : OverallResultsReportColumn {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("name")
                scope = ThScope.col
                text("Name")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(renderName(it.participant)) }
        }
    }
    class CarModel : OverallResultsReportColumn {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("car-model")
                scope = ThScope.col
                text("Car Model")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(it.participant.car.model) }
        }
    }
    class Score : OverallResultsReportColumn {
        override val header: TR.() -> Unit = {
            th {
                classes = setOf("score")
                scope = ThScope.col
                text("Score")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(it.resultsType.title) }
        }
    }
}