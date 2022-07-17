package tech.coner.trailer.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.key
import org.junit.jupiter.api.Test
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.TestParticipants

class TopTimesEventResultsCalculatorTest {

    @Test
    fun `It should calculate Top Times results for LSCC 2019 Simplified Event 1`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val calculator = createTopTimesEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            topTimes().all {
                hasSize(3)
                key(TestClasses.Lscc2019.STREET).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF)
                }
                key(TestClasses.Lscc2019.STREET_TOURING).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points1Simplified.EUGENE_DRAKE)
                }
                key(TestClasses.Lscc2019.NOVICE).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF)
                }
            }
        }
    }

    @Test
    fun `It should calculate Top Times results for LSCC 2019 Simplified Event 2`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points2
        val calculator = createTopTimesEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            topTimes().all {
                hasSize(3)
                key(TestClasses.Lscc2019.STREET).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points2Simplified.BRANDY_HUFF)
                }
                key(TestClasses.Lscc2019.STREET_TOURING).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points2Simplified.REBECCA_JACKSON)
                }
                key(TestClasses.Lscc2019.NOVICE).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points2Simplified.BRANDY_HUFF)
                }
            }
        }
    }

    @Test
    fun `It should calculate Top Times results for LSCC 2019 Simplified Event 3`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points3
        val calculator = createTopTimesEventResultsCalculator(eventContext)

        val actual = calculator.calculate()

        assertThat(actual).all {
            topTimes().all {
                hasSize(3)
                key(TestClasses.Lscc2019.STREET).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points3Simplified.ANASTASIA_RIGLER)
                }
                key(TestClasses.Lscc2019.STREET_TOURING).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points3Simplified.REBECCA_JACKSON)
                }
                key(TestClasses.Lscc2019.NOVICE).all {
                    hasPosition(1)
                    participant().isEqualTo(TestParticipants.Lscc2019Points3Simplified.BRANDY_HUFF)
                }
            }
        }
    }

    private fun createTopTimesEventResultsCalculator(eventContext: EventContext): TopTimesEventResultsCalculator {
        val scoredRunsComparator = ParticipantResult.ScoredRunsComparator(eventContext.extendedParameters.runsPerParticipant)
        val penaltyFactory = StandardPenaltyFactory(eventContext.event.policy)
        val finalScoreFactory = when (eventContext.event.policy.finalScoreStyle) {
            FinalScoreStyle.AUTOCROSS -> AutocrossFinalScoreFactory()
            FinalScoreStyle.RALLYCROSS -> RallycrossFinalScoreFactory()
        }
        return TopTimesEventResultsCalculator(
            eventContext = eventContext,
            overallEventResultsCalculator = when (eventContext.event.policy.topTimesEventResultsMethod) {
                StandardEventResultsTypes.pax -> PaxEventResultsCalculator(
                    eventContext = eventContext,
                    scoredRunsComparator = scoredRunsComparator,
                    runEligibilityQualifier = RunEligibilityQualifier(),
                    runScoreFactory = PaxTimeRunScoreFactory(penaltyFactory),
                    finalScoreFactory = finalScoreFactory
                )
                StandardEventResultsTypes.raw -> RawEventResultsCalculator(
                    eventContext = eventContext,
                    scoredRunsComparatorFactory = scoredRunsComparator,
                    runEligibilityQualifier = RunEligibilityQualifier(),
                    runScoreFactory = RawTimeRunScoreFactory(penaltyFactory),
                    finalScoreFactory = finalScoreFactory
                )
                else -> throw IllegalArgumentException("Unsupported top times event results method")
            }
        )
    }

}