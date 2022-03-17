package tech.coner.trailer.seasonpoints

import assertk.assertThat
import assertk.assertions.isEqualTo
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.mockkParticipantResult
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class EventPointsCalculatorTest {

    enum class LsccGroupedStyleParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST(
                participantResult = mockkParticipantResult(1, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 9
        ),
        SECOND(
                participantResult = mockkParticipantResult(2, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 6
        ),
        THIRD(
                participantResult = mockkParticipantResult(3, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 4
        ),
        FOURTH(
                participantResult = mockkParticipantResult(4, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 3
        ),
        FIFTH(
                participantResult = mockkParticipantResult(5, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 2
        ),
        SIXTH(
                participantResult = mockkParticipantResult(6, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 1
        ),
        DFL(
                participantResult = mockkParticipantResult(Int.MAX_VALUE, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 1
        )
    }

    @ParameterizedTest
    @EnumSource(LsccGroupedStyleParam::class)
    fun `It should calculate points LSCC Grouped-style`(param: LsccGroupedStyleParam) {
        val calculator = TestEventPointsCalculators.lsccGroupedCalculator

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }

    enum class LsccSpecialCupStyleParam(
            val participantResult: ParticipantResult,
            val expected: Int
    ) {
        FIRST(
                participantResult = mockkParticipantResult(1, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 10
        ),
        SECOND(
                participantResult = mockkParticipantResult(2, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 9
        ),
        THIRD(
                participantResult = mockkParticipantResult(3, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 8
        ),
        FOURTH(
                participantResult = mockkParticipantResult(4, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 7
        ),
        FIFTH(
                participantResult = mockkParticipantResult(5, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 6
        ),
        SIXTH(
                participantResult = mockkParticipantResult(6, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 5
        ),
        SEVENTH(
                participantResult = mockkParticipantResult(7, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 4
        ),
        EIGHTH(
                participantResult = mockkParticipantResult(8, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 3
        ),
        NINTH(
                participantResult = mockkParticipantResult(9, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 2
        ),
        TENTH(
                participantResult = mockkParticipantResult(10, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 1
        ),
        ELEVENTH(
                participantResult = mockkParticipantResult(11, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 0
        ),
        DFL(
                participantResult = mockkParticipantResult(Int.MAX_VALUE, participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF),
                expected = 0
        ),
    }

    @ParameterizedTest
    @EnumSource(LsccSpecialCupStyleParam::class)
    fun `It should calculate points LSCC Special Cup-style`(param: LsccSpecialCupStyleParam) {
        val calculator = TestEventPointsCalculators.lsccOverallCalculator

        val actual = calculator.calculate(param.participantResult)

        assertThat(actual).isEqualTo(param.expected)
    }
}