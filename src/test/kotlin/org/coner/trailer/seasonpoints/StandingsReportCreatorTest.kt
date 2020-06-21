package org.coner.trailer.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import org.coner.trailer.TestGroupings
import org.coner.trailer.TestSeasonEvents
import org.coner.trailer.TestSeasons
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
    fun `Create grouped standings sections for LSCC 2019`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
                resultsType = StandardResultsTypes.competitionGrouped,
                season = TestSeasons.lscc2019,
                eventToGroupedResultsReports = mapOf(
                        TestSeasonEvents.Lscc2019.points1 to TestComprehensiveResultsReports.lscc2019Points1.groupedResultsReports.single()
                ),
                takeTopEventScores = 7,
                rankingSort = TestRankingSorts.lscc
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            hasSize(3)
            key(TestGroupings.Lscc2019.NOV).all {
                hasTitle(TestGroupings.Lscc2019.NOV.abbreviation)
                standings().hasSize(3)
            }
            key(TestGroupings.Lscc2019.STR).all {
                hasTitle(TestGroupings.Lscc2019.STR.abbreviation)
                standings().hasSize(2)
            }
            key(TestGroupings.Lscc2019.GS).all {
                hasTitle(TestGroupings.Lscc2019.GS.abbreviation)
                standings().hasSize(2)
            }
        }
    }

    @Test
    fun `Create grouped standings sections for OLSCC 2019`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
                resultsType = StandardResultsTypes.competitionGrouped,
                season = TestSeasons.olscc2019,
                eventToGroupedResultsReports = mapOf(
                        TestSeasonEvents.Olscc2019.points1 to TestComprehensiveResultsReports.olscc2019Points1.groupedResultsReports.single()
                ),
                takeTopEventScores = null,
                rankingSort = TestRankingSorts.olscc
        )

        val actual = creator.createGroupedStandingsSections(param)
    }
}