package org.coner.trailer.seasonpoints

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.coner.trailer.SeasonEvent

class KotlinxHtmlStandingsReportRenderer {

    fun renderContentOnly(report: StandingsReport) = createHTML().div {
        id = "season-points-standings"
        report.sections.forEach { section ->
            standingsReportSection(report, section)
        }
    }

    private fun DIV.standingsReportSection(
            report: StandingsReport,
            section: StandingsReport.Section
    ) = section {
        table {
            caption {
                text(section.title)
            }
            standingsReportSectionTableHeader(report, section)
            standingsReportSectionTableBody(report, section)
        }
    }

    private fun TABLE.standingsReportSectionTableHeader(
            report: StandingsReport,
            section: StandingsReport.Section
    ) = thead {
        tr {
            th {
                colSpan = "2"
            }
            th {
                TODO("report.pointsEventCount")
                colSpan = report.seasonEvents.count { it.points }.toString()
                scope = ThScope.colGroup
                text("Events")
            }
        }
        tr {
            th { text("Name") }
            th { text("Member #") }
            TODO("report.seasonPointsEvents")
            report.seasonEvents
                    .filter { it.points }
                    .forEach { seasonPointsEvent ->
                        th { text(requireNotNull(seasonPointsEvent.eventNumber)) }
                    }
            th { text("Count") }
            th { text("Score") }
        }
    }

    private fun TABLE.standingsReportSectionTableBody(report: StandingsReport, section: StandingsReport.Section) = tbody {
        section.standings.forEach { standing ->
            standingsReportSectionTableRow(report, standing)
        }
    }

    private fun TBODY.standingsReportSectionTableRow(
            report: StandingsReport,
            standing: StandingsReport.Standing) = tr {
        td { text(standing.person.name) }
        td { text(standing.person.memberId) }
        TODO("report.seasonPointsEvents")
        report.seasonEvents
                .filter { it.points }
                .forEach { seasonPointsEvent ->
                    standingsReportSectionTableEventPointsCell(seasonPointsEvent, standing)
                }
        TODO("standing.count")
        td { text(standing.eventToPoints.values.size) }
        td { text(standing.score) }
    }

    private fun TR.standingsReportSectionTableEventPointsCell(seasonEvent: SeasonEvent, standing: StandingsReport.Standing) = td {
        val points = standing.eventToPoints[seasonEvent] ?: return@td
        text(points)
    }

}