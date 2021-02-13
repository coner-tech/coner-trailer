package org.coner.trailer.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.key
import org.coner.trailer.*
import org.coner.trailer.eventresults.*
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
            resultsType = StandardResultsTypes.grouped,
            season = TestSeasons.lscc2019,
            eventToGroupedResultsReports = mapOf(
                TestSeasonEvents.Lscc2019.points1 to TestComprehensiveResultsReports.Lscc2019.points1.groupedResultsReports.single()
            ),
            configuration = TestSeasonPointsCalculatorConfigurations.lscc2019
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
    fun `It should create grouped standings sections with LSCC 2019-style tie breaking`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
            resultsType = StandardResultsTypes.grouped,
            season = TestSeasons.lscc2019,
            eventToGroupedResultsReports = mapOf(
                TestSeasonEvents.LsccTieBreaking.points1 to TestComprehensiveResultsReports.LsccTieBreaking.points1.groupedResultsReports.single(),
                TestSeasonEvents.LsccTieBreaking.points2 to TestComprehensiveResultsReports.LsccTieBreaking.points2.groupedResultsReports.single()

            ),
            configuration = TestSeasonPointsCalculatorConfigurations.lscc2019
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            key(TestGroupings.Lscc2019.HS).all {
                standings().all {
                    hasSize(5)
                    index(0).all {
                        hasPosition(1)
                        isTie()
                        hasPerson(TestPeople.REBECCA_JACKSON)
                    }
                    index(1).all {
                        hasPosition(1)
                        isTie()
                        hasPerson(TestPeople.JIMMY_MCKENZIE)
                    }
                    index(2).all {
                        hasPosition(2)
                        isNotTie()
                        hasPerson(TestPeople.EUGENE_DRAKE)
                    }
                    index(3).all {
                        hasPosition(3)
                        isTie()
                        hasPerson(TestPeople.TERI_POTTER)
                    }
                    index(4).all {
                        hasPosition(3)
                        isTie()
                        hasPerson(TestPeople.HARRY_WEBSTER)
                    }
                }
            }
        }
    }

    @Test
    fun `It should exclude participants not eligible for season points`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
            resultsType = StandardResultsTypes.grouped,
            season = TestSeasons.lscc2019,
            eventToGroupedResultsReports = mapOf(
                TestSeasonEvents.LsccTieBreaking.points1 to GroupedResultsReport(
                    type = StandardResultsTypes.grouped,
                    groupingsToResultsMap = sortedMapOf(
                        TestGroupings.Lscc2019.HS to listOf(
                            ParticipantResult(
                                position = 1,
                                score = Score("0.000"),
                                participant = TestParticipants.Lscc2019Points1.TERI_POTTER.copy(
                                    seasonPointsEligible = false // only value relevant to test
                                ),
                                scoredRuns = listOf(ResultRun(Time("45.678"), personalBest = true)),
                                marginOfVictory = null,
                                marginOfLoss = null
                            ),
                            ParticipantResult( // to make sure eligible are included
                                position = 2,
                                score = Score("0.000"),
                                participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
                                scoredRuns = listOf(ResultRun(Time("56.789"), personalBest = true)),
                                marginOfVictory = null,
                                marginOfLoss = null
                            )
                        )
                    )
                )
            ),
            configuration = TestSeasonPointsCalculatorConfigurations.lscc2019
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            key(TestGroupings.Lscc2019.HS).standings().all {
                hasSize(1)
                index(0).hasPerson(TestPeople.REBECCA_JACKSON)
            }
        }
    }

    @Test
    fun `Create grouped standings sections for OLSCC 2019`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
            resultsType = StandardResultsTypes.grouped,
            season = TestSeasons.olscc2019,
            eventToGroupedResultsReports = mapOf(
                TestSeasonEvents.Olscc2019.points1 to TestComprehensiveResultsReports.Olscc2019.points1.groupedResultsReports.single()
            ),
            configuration = TestSeasonPointsCalculatorConfigurations.olsccV1
        )

        val actual = creator.createGroupedStandingsSections(param)

        // actual results not accessible at time of coding.
        // punting until further notice.
    }
}