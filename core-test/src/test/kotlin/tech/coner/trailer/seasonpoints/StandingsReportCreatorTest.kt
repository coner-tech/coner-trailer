package tech.coner.trailer.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.key
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.coner.trailer.*
import tech.coner.trailer.eventresults.*

class StandingsReportCreatorTest {

    lateinit var creator: StandingsReportCreator

    @BeforeEach
    fun before() {
        creator = StandingsReportCreator()
    }

    @Test
    fun `Create grouped standings sections for LSCC 2019`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
            eventResultsType = StandardEventResultsTypes.clazz,
            season = TestSeasons.lscc2019,
            eventToClassEventResults = mapOf(
                TestSeasonEvents.Lscc2019.points1 to TestComprehensiveEventResults.Lscc2019.points1.classEventResults
            ),
            configuration = TestSeasonPointsCalculatorConfigurations.lscc2019
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            hasSize(3)
            key(TestClasses.Lscc2019.NOV).all {
                hasTitle(TestClasses.Lscc2019.NOV.abbreviation)
                standings().hasSize(3)
            }
            key(TestClasses.Lscc2019.STR).all {
                hasTitle(TestClasses.Lscc2019.STR.abbreviation)
                standings().hasSize(2)
            }
            key(TestClasses.Lscc2019.GS).all {
                hasTitle(TestClasses.Lscc2019.GS.abbreviation)
                standings().hasSize(2)
            }
        }
    }

    @Test
    fun `It should create grouped standings sections with LSCC 2019-style tie breaking`() {
        val param = StandingsReportCreator.CreateGroupedStandingsSectionsParameters(
            eventResultsType = StandardEventResultsTypes.clazz,
            season = TestSeasons.lscc2019,
            eventToClassEventResults = mapOf(
                TestSeasonEvents.LsccTieBreaking.points1 to TestComprehensiveEventResults.LsccTieBreaking.points1.classEventResults,
                TestSeasonEvents.LsccTieBreaking.points2 to TestComprehensiveEventResults.LsccTieBreaking.points2.classEventResults

            ),
            configuration = TestSeasonPointsCalculatorConfigurations.lscc2019
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            key(TestClasses.Lscc2019.HS).all {
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
            eventResultsType = StandardEventResultsTypes.clazz,
            season = TestSeasons.lscc2019,
            eventToClassEventResults = mapOf(
                TestSeasonEvents.LsccTieBreaking.points1 to ClassEventResults(
                    eventContext = TestEventContexts.LsccTieBreaking.points1,
                    groupParticipantResults = sortedMapOf(
                        TestClasses.Lscc2019.HS to listOf(
                            testParticipantResult(
                                position = 1,
                                score = Score("45.678"),
                                participant = TestParticipants.Lscc2019Points1.TERI_POTTER.copy(
                                    seasonPointsEligible = false // only value relevant to test
                                ),
                                runFns = listOf { participant ->
                                    testRunWithScore(
                                        sequence = 1,
                                        score = Score("45.678"),
                                        participant = participant,
                                        time = Time("45.678"),
                                    )
                                },
                                diffPrevious = null,
                                diffFirst = null,
                                personalBestScoredRunIndex = 0
                            ),
                            testParticipantResult( // to make sure eligible are included
                                position = 2,
                                score = Score("56.789"),
                                participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
                                runFns = listOf { participant ->
                                    testRunWithScore(
                                        sequence = 2,
                                        participant = participant,
                                        score = Score("56.789"),
                                        time = Time("56.789"),
                                    )
                                },
                                diffFirst = null,
                                diffPrevious = null,
                                personalBestScoredRunIndex = 0
                            )
                        )
                    ),
                )
            ),
            configuration = TestSeasonPointsCalculatorConfigurations.lscc2019
        )

        val actual = creator.createGroupedStandingsSections(param)

        assertThat(actual).all {
            key(TestClasses.Lscc2019.HS).standings().all {
                hasSize(1)
                index(0).hasPerson(TestPeople.REBECCA_JACKSON)
            }
        }
    }
}
