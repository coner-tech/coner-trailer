package org.coner.trailer.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.TestGroupings
import org.coner.trailer.eventresults.TestEventComprehensiveResultsReports
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StandingsReportCreatorTest {

    lateinit var creator: StandingsReportCreator

    @BeforeEach
    fun before() {
        creator = StandingsReportCreator()
    }

    @Test
    fun `Create grouped standings sections`() {
        val eventNumberToGroupedResultsReports = mapOf(
                1 to TestEventComprehensiveResultsReports.THSCC_2019_POINTS_1.groupedResults
        )

        val actual = creator.createGroupedStandingsSections(eventNumberToGroupedResultsReports)

        assertThat(actual).all {
            hasSize(3)
            exactly(1) {
                it.hasTitle(TestGroupings.THSCC_2019_NOV.abbreviation)
            }
            exactly(1) {
                it.hasTitle(TestGroupings.THSCC_2019_STR.abbreviation)
            }
            exactly(1) {
                it.hasTitle(TestGroupings.THSCC_2019_GS.abbreviation)
            }
        }
    }
}