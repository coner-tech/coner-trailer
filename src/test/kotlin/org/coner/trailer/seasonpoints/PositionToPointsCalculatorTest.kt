package org.coner.trailer.seasonpoints

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.coner.trailer.TestParticipants
import org.coner.trailer.eventresults.ParticipantResult
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class PositionToPointsCalculatorTest {

    enum class LsccGroupingStyleParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST(
                participantResult = ParticipantResult.Minimal(1, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 9
        ),
        SECOND(
                participantResult = ParticipantResult.Minimal(2, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 6
        ),
        THIRD(
                participantResult = ParticipantResult.Minimal(3, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 4
        ),
        FOURTH(
                participantResult = ParticipantResult.Minimal(4, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 3
        ),
        FIFTH(
                participantResult = ParticipantResult.Minimal(5, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 2
        ),
        SIXTH(
                participantResult = ParticipantResult.Minimal(6, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 1
        ),
        DFL(
                participantResult = ParticipantResult.Minimal(Int.MAX_VALUE, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 1
        )
    }

    @ParameterizedTest
    @EnumSource(LsccGroupingStyleParam::class)
    fun `It should calculate points LSCC Grouping-style`(param: LsccGroupingStyleParam) {
        val calculator = TestParticipantResultPositionMappedPointsCalculators.LSCC_GROUPING_CALCULATOR

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }

    enum class LsccSpecialCupStyleParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST(
                participantResult = ParticipantResult.Minimal(1, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 10
        ),
        SECOND(
                participantResult = ParticipantResult.Minimal(2, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 9
        ),
        THIRD(
                participantResult = ParticipantResult.Minimal(3, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 8
        ),
        FOURTH(
                participantResult = ParticipantResult.Minimal(4, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 7
        ),
        FIFTH(
                participantResult = ParticipantResult.Minimal(5, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 6
        ),
        SIXTH(
                participantResult = ParticipantResult.Minimal(6, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 5
        ),
        SEVENTH(
                participantResult = ParticipantResult.Minimal(7, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 4
        ),
        EIGHTH(
                participantResult = ParticipantResult.Minimal(8, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 3
        ),
        NINTH(
                participantResult = ParticipantResult.Minimal(9, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 2
        ),
        TENTH(
                participantResult = ParticipantResult.Minimal(10, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 1
        ),
        ELEVENTH(
                participantResult = ParticipantResult.Minimal(11, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 0
        ),
        DFL(
                participantResult = ParticipantResult.Minimal(Int.MAX_VALUE, TestParticipants.Thscc2019Points1.BRANDY_HUFF),
                expected = 0
        ),
    }

    @ParameterizedTest
    @EnumSource(LsccSpecialCupStyleParam::class)
    fun `It should calculate points LSCC Special Cup-style`(param: LsccSpecialCupStyleParam) {
        val calculator = TestParticipantResultPositionMappedPointsCalculators.LSCC_OVERALL_CALCULATOR

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }
}