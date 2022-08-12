package tech.coner.trailer.render.html

import kotlinx.html.*
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.EventResultsColumnRenderer

abstract class HtmlEventResultsColumn : EventResultsColumnRenderer<
        TR.(EventResultsType) -> Unit,
        TR.(ParticipantResult) -> Unit
        > {

    open fun buildStyles(event: Event, results: EventResults): Set<String> = emptySet()

    class Position : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary .position {
                    display: none;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("position")
                scope = ThScope.col
                text("Pos.")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            th {
                classes = setOf("position")
                scope = ThScope.row
                text(it.position)
            }
        }
    }

    class Signage(private val responsive: Boolean) : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults): Set<String> {
            return if (responsive) {
                setOf(
                    """
                    @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                        .event-results table.primary th.signage, .event-results table.primary td.signage {
                            display: none;
                        }
                    }
                    """.trimIndent()
                )
            } else {
                emptySet()
            }
        }
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("signage")
                scope = ThScope.col
                text("Signage")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("signage")
                text(it.participant.signage?.classingNumber ?: "")
            }
        }
    }

    class MobilePositionSignage : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            .event-results table.primary th.mobile-position-signage, .event-results table.primary td.mobile-position-signage {
                display: none;
            }
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary th.mobile-position-signage {
                    display: table-cell;
                }
                .event-results table.primary th.mobile-position-signage span {
                    display: block;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("mobile-position-signage")
                scope = ThScope.col
                text("Position / Signage")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            th {
                classes = setOf("mobile-position-signage")
                scope = ThScope.row
                span {
                    classes = setOf("position")
                    text(it.position)
                }
                span {
                    classes = setOf("signage")
                    text(it.participant.signage?.classingNumber ?: "")
                }
            }
        }
    }

    class SignageClass : HtmlEventResultsColumn() {
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("signage", "signage-category-handicap")
                scope = ThScope.col
                text("Class")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(it.participant.signage?.classing?.abbreviation ?: "") }
        }
    }

    class SignageNumber : HtmlEventResultsColumn() {
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("signage", "signage-number")
                scope = ThScope.col
                text("Number")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(it.participant.signage?.number ?: "") }
        }
    }

    class Name(private val responsive: Boolean) : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults): Set<String> {
            return if (responsive) {
                setOf(
                    """
                    @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                        .event-results table.primary th.name, .event-results table.primary td.name {
                            display: none;
                        }
                    } 
                    """.trimIndent()
                )
            } else {
                emptySet()
            }
        }
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("name")
                scope = ThScope.col
                text("Name")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("name")
                text(renderName(it.participant))
            }
        }
    }
    class CarModel : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary th.car-model, .event-results table.primary td.car-model {
                    display: none;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("car-model")
                scope = ThScope.col
                text("Car Model")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("car-model")
                text(it.participant.car?.model ?: "")
            }
        }
    }
    class MobileNameCarModel : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            """
            .event-results table.primary th.mobile-name-car-model, .event-results table.primary td.mobile-name-car-model {
                display: none;
            }
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                .event-results table.primary th.mobile-name-car-model, .event-results table.primary td.mobile-name-car-model {
                    display: table-cell;
                }
                .event-results table.primary td.mobile-name-car-model span {
                    display: block;
                }
            }
            """.trimIndent()
        )

        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("mobile-name-car-model")
                scope = ThScope.col
                text("Name / Car")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("mobile-name-car-model")
                span {
                    classes = setOf("name")
                    text(renderName(it.participant))
                }
                span {
                    classes = setOf("car-model")
                    text(it.participant.car?.model ?: "")
                }
            }
        }
    }

    class Score : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            CommonStyles.time,
            """
            .event-results table.primary td.score {
                font-weight: bold;
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
    class DiffFirst : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            CommonStyles.time,
            CommonStyles.hideDiffOnMobile
        )
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("diff", "diff-first")
                scope = ThScope.col
                text("Diff. - 1st")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("time", "diff", "diff-first")
                text(render(it.diffFirst))
            }
        }
    }
    class DiffPrevious : HtmlEventResultsColumn() {
        override fun buildStyles(event: Event, results: EventResults) = setOf(
            CommonStyles.time,
            CommonStyles.hideDiffOnMobile
        )
        override val header: TR.(EventResultsType) -> Unit = {
            th {
                classes = setOf("diff", "diff-previous")
                scope = ThScope.col
                text("Diff. - Prev.")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("time", "diff", "diff-previous")
                text(render(it.diffPrevious))
            }
        }
    }

    private object CommonStyles {
        val time = """
            .time {
                font-family: monospace;
            }
            """.trimIndent()
        val hideDiffOnMobile = """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                th.diff, td.diff {
                    display: none;
                }
            }
        """.trimIndent()
    }
}