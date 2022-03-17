package tech.coner.trailer.seasonpoints

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import tech.coner.trailer.SeasonEvent

class KotlinxHtmlStandingsReportRenderer {

    fun renderContentOnly(report: StandingsReport) = createHTML().div {
        id = "season-points-standings"
        report.sections.forEach { section ->
            standingsReportSection(report, section)
        }
        legend()
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
                colSpan = "3"
            }
            th {
                colSpan = report.pointsEvents.size.toString()
                scope = ThScope.colGroup
                text("Events")
            }
        }
        tr {
            th { abbr { title = "Position"; text("Pos.") } }
            th { text("Name") }
            th { text("Member #") }
            report.pointsEvents.forEach { pointsEvents ->
                th { text(requireNotNull(pointsEvents.eventNumber)) }
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
        td {
            text(standing.position)
            if (standing.tie) {
                a("#legend-tie") { text("*") }
            }
        }
        td { text("${standing.person.firstName} ${standing.person.lastName}") }
        td { text(standing.person.clubMemberId ?: "") }
        report.pointsEvents.forEach { pointsEvent ->
            standingsReportSectionTableEventPointsCell(pointsEvent, standing)
        }
        td { text(standing.eventToPoints.size) }
        td { text(standing.score) }
    }

    private fun TR.standingsReportSectionTableEventPointsCell(seasonEvent: SeasonEvent, standing: StandingsReport.Standing) = td {
        val points = standing.eventToPoints[seasonEvent] ?: return@td
        text(points)
    }

    private fun DIV.legend() = dl {
        dt {
            id = "legend-tie"
            text("*")
        }
        dd {
            text("Tie")
        }
    }

}