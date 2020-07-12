package org.coner.trailer.datasource.crispyfish.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.corresponds
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.key
import org.coner.trailer.TestGroupings
import org.coner.trailer.TestPeople
import org.coner.trailer.datasource.crispyfish.eventsresults.CompetitionGroupedResultsReportCreator
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.seasonpoints.*
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

        assertThat(actual).all {
            key(TestGroupings.Lscc2019.NOV).all {
                standings().all {
                    hasSize(4)
                    index(0).all {
                        hasPosition(1)
                        isNotTie()
                        hasScore(18)
                        person().corresponds(TestPeople.BRANDY_HUFF) { actual, expected -> actual.id == expected.id }
                    }
                    index(1).all {
                        hasPosition(2)
                        isNotTie()
                        hasScore(12)
                        person().corresponds(TestPeople.BRYANT_MORAN) { actual, expected -> actual.id == expected.id }
                    }
                    index(2).all {
                        hasPosition(3)
                        isNotTie()
                        hasScore(10)
                        person().corresponds(TestPeople.DOMINIC_ROGERS) { actual, expected -> actual.id == expected.id }
                    }
                    index(3).all {
                        hasPosition(4)
                        isNotTie()
                        hasScore(7)
                        person().corresponds(TestPeople.BENNETT_PANTONE) { actual, expected -> actual.id == expected.id }
                    }
                }
            }
        }
    }
}