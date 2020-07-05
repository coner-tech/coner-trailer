package org.coner.trailer.seasonpoints

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.coner.trailer.TestParticipantResultPointsCalculators
import org.coner.trailer.TestParticipants
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.mockkParticipantResult
import org.coner.trailer.eventresults.mockkResultRun
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ParticipantResultPointsCalculatorTest {

    enum class LsccGroupingStyleParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST(
                participantResult = mockkParticipantResult(1, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 9
        ),
        SECOND(
                participantResult = mockkParticipantResult(2, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 6
        ),
        THIRD(
                participantResult = mockkParticipantResult(3, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 4
        ),
        FOURTH(
                participantResult = mockkParticipantResult(4, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 3
        ),
        FIFTH(
                participantResult = mockkParticipantResult(5, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 2
        ),
        SIXTH(
                participantResult = mockkParticipantResult(6, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 1
        ),
        DFL(
                participantResult = mockkParticipantResult(Int.MAX_VALUE, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 1
        )
    }

    @ParameterizedTest
    @EnumSource(LsccGroupingStyleParam::class)
    fun `It should calculate points LSCC Grouping-style`(param: LsccGroupingStyleParam) {
        val calculator = TestParticipantResultPointsCalculators.lsccGroupingCalculator

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }

    enum class LsccSpecialCupStyleParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST(
                participantResult = mockkParticipantResult(1, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 10
        ),
        SECOND(
                participantResult = mockkParticipantResult(2, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 9
        ),
        THIRD(
                participantResult = mockkParticipantResult(3, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 8
        ),
        FOURTH(
                participantResult = mockkParticipantResult(4, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 7
        ),
        FIFTH(
                participantResult = mockkParticipantResult(5, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 6
        ),
        SIXTH(
                participantResult = mockkParticipantResult(6, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 5
        ),
        SEVENTH(
                participantResult = mockkParticipantResult(7, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 4
        ),
        EIGHTH(
                participantResult = mockkParticipantResult(8, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 3
        ),
        NINTH(
                participantResult = mockkParticipantResult(9, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 2
        ),
        TENTH(
                participantResult = mockkParticipantResult(10, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 1
        ),
        ELEVENTH(
                participantResult = mockkParticipantResult(11, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 0
        ),
        DFL(
                participantResult = mockkParticipantResult(Int.MAX_VALUE, TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 0
        ),
    }

    @ParameterizedTest
    @EnumSource(LsccSpecialCupStyleParam::class)
    fun `It should calculate points LSCC Special Cup-style`(param: LsccSpecialCupStyleParam) {
        val calculator = TestParticipantResultPointsCalculators.lsccOverallCalculator

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }

    enum class NlsccParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST_CLEAN(
                participantResult = mockkParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun()
                ),
                expected = 20
        ),
        FIRST_DNF(
                participantResult = mockkParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun(didNotFinish = true)
                ),
                expected = 1
        ),
        FIRST_DNS(
                participantResult = mockkParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = null
                ),
                expected = 0
        ),
        SECOND_CLEAN(
                participantResult = mockkParticipantResult(
                        position = 2,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun()
                ),
                expected = 16
        ),
        FIFTH_CLEAN(
                participantResult = mockkParticipantResult(
                        position = 5,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun()
                ),
                expected = 9
        ),
        TENTH_CLEAN(
                participantResult = mockkParticipantResult(
                        position = 10,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun()
                ),
                expected = 2
        ),
        ELEVENTH_CLEAN(
                participantResult = mockkParticipantResult(
                        position = 11,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun()
                ),
                expected = 1
        ),
        TWELFTH_CLEAN(
                participantResult = mockkParticipantResult(
                        position = 12,
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        personalBestRun = mockkResultRun()
                ),
                expected = 1
        )
    }

    @ParameterizedTest
    @EnumSource(NlsccParam::class)
    fun `It should calculate points NLSCC-style`(param: NlsccParam) {
        val calculator = TestParticipantResultPointsCalculators.olsccCalculator

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }
}