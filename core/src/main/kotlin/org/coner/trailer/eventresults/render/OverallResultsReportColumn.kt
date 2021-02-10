package org.coner.trailer.eventresults.render

import kotlinx.html.*
import org.coner.trailer.eventresults.ParticipantResult

interface OverallResultsReportColumn {

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
            td {
                text(it.participant.signage.render())
            }
        }

    }
}