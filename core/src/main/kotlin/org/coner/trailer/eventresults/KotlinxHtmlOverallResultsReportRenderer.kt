package org.coner.trailer.eventresults

import kotlinx.html.*
import kotlinx.html.stream.createHTML

class KotlinxHtmlOverallResultsReportRenderer {

    fun renderContentOnly(report: OverallResultsReport) = createHTML().div {
        id = "overall-results-report"
        table {
            caption { text(report.type.title) }
            thead {
                tr {
                    th { text("Pos.") }
                    th { text("Handicap") }
                    th { text("#") }
                    th { text("Name") }
                    th { text("Car Model") }
                    th { text(report.type.scoreColumnHeading) }
                }
            }
            tbody {
                for (result in report.participantResults) {
                    tr {
                        td { text(result.position) }
                        td { text(result.participant.signage.handicap.abbreviation) }
                        td { text(result.participant.signage.number) }
                        td { text("${result.participant.firstName} ${result.participant.lastName}") }
                        td { text(result.participant.car.model) }
                        td { text(result.scoreColumnValue) }
                    }
                }
            }
        }
    }
}