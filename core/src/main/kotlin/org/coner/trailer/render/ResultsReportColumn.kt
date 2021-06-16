package org.coner.trailer.render

import kotlinx.html.*
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.ResultsReport
import org.coner.trailer.eventresults.ResultsType

abstract class ResultsReportColumn : Renderer {

    open fun buildStyles(report: ResultsReport): Set<String> = emptySet()
    
    abstract val header: TR.(ResultsType) -> Unit

    abstract val data: TR.(ParticipantResult) -> Unit

    class Position : ResultsReportColumn() {
        override val header: TR.(ResultsType) -> Unit = {
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

    class Signage : ResultsReportColumn() {
        override val header: TR.(ResultsType) -> Unit = {
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
    class Score : ResultsReportColumn() {
        override fun buildStyles(report: ResultsReport) = setOf(CommonStyles.time)
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
        override fun buildStyles(report: ResultsReport) = setOf(CommonStyles.time)
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("diff", "diff-first")
                scope = ThScope.col
                text("Diff. - 1st")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("time")
                text(render(it.diffFirst))
            }
        }
    }
    class DiffPrevious : ResultsReportColumn() {
        override fun buildStyles(report: ResultsReport) = setOf(CommonStyles.time)
        override val header: TR.(ResultsType) -> Unit = {
            th {
                classes = setOf("diff", "diff-previous")
                scope = ThScope.col
                text("Diff. - Prev.")
            }
        }
        override val data: TR.(ParticipantResult) -> Unit = {
            td {
                classes = setOf("time")
                text(render(it.diffPrevious))
            }
        }
    }

    class Runs : ResultsReportColumn() {
        override fun buildStyles(report: ResultsReport) = setOf(
            CommonStyles.time,
            """
            ol.runs {
                padding: 0;
                display: grid;
                grid-template-columns: repeat(${report.runCount}, 1fr);
                list-style-type: none;
                list-style-position: inside;
            }
            ol.runs li {
                min-width: 110px;
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
    }
}