package org.coner.trailer.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.TestGroupings
import org.coner.trailer.TestSeasonEvents
import org.coner.trailer.TestSeasons
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.eventresults.TestComprehensiveResultsReports
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
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
                resultsType = StandardResultsTypes.competitionGrouped,
                season = TestSeasons.LSCC_2019,
                eventToGroupedResultsReports = mapOf(
                        TestSeasonEvents.LSCC_2019.POINTS1 to TestComprehensiveResultsReports.THSCC_2019_POINTS_1.groupedResultsReports.single()
                ),
                takeTopEventScores = 7,
                rankingSort = TestRankingSorts.LSCC
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            hasSize(3)
            key(TestGroupings.THSCC_2019_NOV).all {
                hasTitle(TestGroupings.THSCC_2019_NOV.abbreviation)
                standings().hasSize(3)
            }
            key(TestGroupings.THSCC_2019_STR).all {
                hasTitle(TestGroupings.THSCC_2019_STR.abbreviation)
                standings().hasSize(2)
            }
            key(TestGroupings.THSCC_2019_GS).all {
                hasTitle(TestGroupings.THSCC_2019_GS.abbreviation)
                standings().hasSize(2)
            }
        }
    }
}