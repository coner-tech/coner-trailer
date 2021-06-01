package org.coner.trailer.datasource.crispyfish.eventresults

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.TestParticipants
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.datasource.crispyfish.eventsresults.ResultRunMapper
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.Run

@ExtendWith(MockKExtension::class)
class ResultRunMapperTest {
    
    lateinit var mapper: ResultRunMapper

    @MockK lateinit var cfRunMapper: CrispyFishRunMapper
    @MockK lateinit var runEligibilityQualifier: RunEligibilityQualifier
    @MockK lateinit var runScoreFactory: RunScoreFactory

    @BeforeEach
    fun before() {
        mapper = ResultRunMapper(
            cfRunMapper = cfRunMapper,
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = runScoreFactory
        )
    }

    @Nested
    inner class ToCoreTests {

        @Test
        fun `It should map a clean eligible run`() {
            val context: CrispyFishEventMappingContext = mockk {
                every { runCount } returns 1
            }
            val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
            val cfRun = testCfRun(
                timeScratchAsString = "123.456"
            )
            val run: org.coner.trailer.Run = mockk()
            every {
                cfRunMapper.toCore(
                    cfRun = cfRun, // Run #1
                    cfRunIndex = 0, // Run #1
                    participant = participant
                )
            } returns run
            every {
                runEligibilityQualifier.check(
                    run = run,
                    participantResultRunIndex = 0,
                    maxRunCount = 1,
                )
            } returns true
            val expectedScore: Score = mockk()
            every {
                runScoreFactory.score(run = run)
            } returns expectedScore

            val actual = mapper.toCore(
                context = context,
                cfRun = cfRun, // Run #1
                cfRunIndex = 0, // Run #1
                participantResultRunIndex = 0, // Run #1
                participant = participant
            )

            assertThat(actual).isNotNull().all {
                score().isSameAs(expectedScore)
                run().isSameAs(run)
            }
            verifySequence {
                cfRunMapper.toCore(
                    cfRun = cfRun,
                    cfRunIndex = 0,
                    participant = participant
                )
                runEligibilityQualifier.check(
                    run = run,
                    participantResultRunIndex = 0,
                    maxRunCount = 1
                )
                runScoreFactory.score(run)
            }
        }

        @Test
        fun `When run is not eligible, it should return null`() {
            val context: CrispyFishEventMappingContext = mockk {
                every { runCount } returns 1
            }
            val participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON
            val cfRun = testCfRun(
                timeScratchAsString = "123.456"
            )
            val expectedRun: org.coner.trailer.Run = mockk()
            every {
                cfRunMapper.toCore(
                    cfRun = cfRun, // Run #1
                    cfRunIndex = 0, // Run #1
                    participant = participant
                )
            } returns expectedRun
            every {
                runEligibilityQualifier.check(
                    run = expectedRun,
                    participantResultRunIndex = 0,
                    maxRunCount = 1,
                )
            } returns false

            val actual = mapper.toCore(
                context = context,
                cfRun = cfRun,
                cfRunIndex = 0,
                participantResultRunIndex = 0,
                participant = participant
            )

            assertThat(actual).isNull()
            verifySequence {
                cfRunMapper.toCore(
                    cfRun = cfRun,
                    cfRunIndex = 0,
                    participant = participant
                )
                runEligibilityQualifier.check(
                    run = expectedRun,
                    participantResultRunIndex = 0,
                    maxRunCount = 1
                )
            }
        }
    }

    @Nested
    inner class ToCoresTests {

    }
}

private fun testCfRun(
    timeScratchAsString: String? = null,
    penaltyType: PenaltyType? = null,
    cones: Int? = null
) = Run(
    timeScratchAsString = timeScratchAsString,
    penaltyType = penaltyType,
    number = null, // unused
    rawTime = null, // unused
    paxTime = null, // unused
    cones = cones,
    timeScored = null, // unused
    timeScratchAsDuration = null // unused
)
