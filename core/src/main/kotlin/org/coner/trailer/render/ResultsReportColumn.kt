package org.coner.trailer.render

import kotlinx.html.*
import org.coner.trailer.Event
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.ResultsReport
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.render.kotlinxhtml.MediaSize

abstract class ResultsReportColumn : Renderer {

    open fun buildStyles(event: Event, report: ResultsReport): Set<String> = emptySet()
    
    abstract val header: TR.(ResultsType) -> Unit

    abstract val data: TR.(ParticipantResult) -> Unit

    class Position : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(
            """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                th.position {
                    display: none;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(ResultsType) -> Unit = {
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

    class Signage : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(
            """
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                th.signage, td.signage {
                    display: none;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("signage")
                scope = ThScope.col
                text("Signage")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("signage")
                text(render(it.participant.signage))
            }
        }
    }

    class MobilePositionSignage : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(
            """
            th.mobile-position-signage, td.mobile-position-signage {
                display: none;
            }
            @media screen and (max-width: ${MediaSize.MOBILE_MAX}px) {
                th.mobile-position-signage {
                    display: table-cell;
                }
                th.mobile-position-signage span {
                    display: block;
                }
            }
            """.trimIndent()
        )
        override val header: TR.(ResultsType) -> Unit = {
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
                    text(render(it.participant.signage))
                }
            }
        }
    }

    class SignageClass : ResultsReportColumn() {
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("signage", "signage-category-handicap")
                scope = ThScope.col
                text("Class")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(it.participant.signage?.grouping?.abbreviation ?: "") }
        }
    }

    class SignageNumber : ResultsReportColumn() {
        override val header: TR.(ResultsType) -> Unit = {
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

    class Name : ResultsReportColumn() {
        override val header: TR.(ResultsType) -> Unit = {
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
    class CarModel : ResultsReportColumn() {
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("car-model")
                scope = ThScope.col
                text("Car Model")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td { text(it.participant.car.model ?: "") }
        }
    }
    // TODO: MobileNameCarModel

    class Score : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(CommonStyles.time)
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("score")
                scope = ThScope.col
                text(it.scoreColumnHeading)
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("time")
                text(renderScoreColumnValue(it))
            }
        }
    }
    class DiffFirst : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(
            CommonStyles.time,
            CommonStyles.hideDiffOnMobile
        )
        override val header: TR.(ResultsType) -> Unit = {
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
    class DiffPrevious : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(
            CommonStyles.time,
            CommonStyles.hideDiffOnMobile
        )
        override val header: TR.(ResultsType) -> Unit = {
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

    class Runs : ResultsReportColumn() {
        override fun buildStyles(event: Event, report: ResultsReport) = setOf(
            CommonStyles.time,
            """
            .event-${event.id} th.runs, .event-${event.id} td.runs {
                display: none;
            }
            @media screen and (min-width: ${MediaSize.MOBILE_MAX}px) {
                .event-${event.id} th.runs, .event-${event.id} td.runs  {
                    display: table-cell;
                }
                .event-${event.id} ol.runs {
                    padding: 0;
                    display: grid;
                    grid-template-columns: 1;
                    list-style-type: none;
                    list-style-position: inside;
                }
                ol.runs li {
                    min-width: 110px;
                }
                @media screen and (min-width: 1280px) {
                    .event-${event.id} ol.runs {
                        grid-template-columns: repeat(${report.runCount}, 1fr);
                    }
                }
            }
            """.trimIndent()
        )
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("runs")
                scope = ThScope.col
                text("Runs")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("runs")
                ol {
                    classes = setOf("runs", "time")
                    for (run in it.allRuns) {
                        render(run)?.let { runText ->
                            li {
                                text(runText)
                            }
                        }
                    }
                }
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