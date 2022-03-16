package org.coner.trailer.render.html

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.EventResults
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.ParticipantResult

abstract class HtmlIndividualEventResultsColumn : HtmlEventResultsColumn() {

    class Position : HtmlIndividualEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary th.position, .event-results table.primary td.position {
                    display: none;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(EventResultsType) -> Unit = { eventResultsType ->
            th {
                classes = setOf("position")
                text(eventResultsType.positionColumnHeading)
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("position")
                text(it.position)
            }
        }
    }

    class Score : HtmlIndividualEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary th.score, .event-results table.primary td.score {
                    display: none;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("score")
                scope = ThScope.col
                text(it.scoreColumnHeading)
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("time", "score")
                text(renderScoreColumnValue(it))
            }
        }
    }

    class MobilePositionScore : HtmlIndividualEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            .event-results table.primary th.mobile-position-score, .event-results table.primary td.mobile-position-score {
                display: none;
            }
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary th.mobile-position-score, .event-results table.primary td.mobile-position-score {
                    display: table-cell;
                }
                .event-results table.primary td.mobile-position-score span {
                    display: block;
                }
            }
            """.trimIndent()
        )

        override val header: TR.(EventResultsType) -> Unit = { eventResultsType ->
            th {
                classes = setOf("mobile-position-score")
                scope = ThScope.col
                text("${eventResultsType.positionColumnHeading} / ${eventResultsType.scoreColumnHeading}")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("mobile-position-score")
                span {
                    classes = setOf("position")
                    text(it.position)
                }
                span {
                    classes = setOf("time", "score")
                    text(renderScoreColumnValue(it))
                }
            }
        }
    }
}