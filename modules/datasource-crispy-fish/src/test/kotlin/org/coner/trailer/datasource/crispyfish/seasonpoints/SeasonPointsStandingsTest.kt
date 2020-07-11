package org.coner.trailer.datasource.crispyfish.seasonpoints

import assertk.all
import assertk.assertThat
import org.coner.trailer.datasource.crispyfish.eventsresults.CompetitionGroupedResultsReportCreator
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.seasonpoints.StandingsReportCreator
import org.coner.trailer.seasonpoints.TestRankingSorts
import org.junit.jupiter.api.Test

class SeasonPointsStandingsTest {

    @Test
    fun `It should produce season points standings for LSCC 2019 Simplified`() {
        val seasonFixture = SeasonFixture.Lscc2019Simplified
        val competitionGroupedResultsReportCreator = CompetitionGroupedResultsReportCreator()
        val competitionGroupedResultsReports = seasonFixture.events.map { eventFixture ->
            eventFixture.coreSeasonEvent to competitionGroupedResultsReportCreator.createFromRegistrationData(
                    crispyFishRegistrations = eventFixture.registrations(seasonFixture),
                    memberIdToPeople = seasonFixture.memberIdToPeople
            )
        }.toMap()
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
                resultsType = StandardResultsTypes.competitionGrouped,
                season = SeasonFixture.Lscc2019Simplified.season,
                eventToGroupedResultsReports = competitionGroupedResultsReports,
                rankingSort = TestRankingSorts.lscc,
                takeTopEventScores = 2
        )
        val creator = StandingsReportCreator()

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {  }
    }
}