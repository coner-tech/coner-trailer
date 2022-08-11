package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.*
import java.math.BigDecimal

class LegacyBuggedPaxTimeRunScoreFactoryTest {

    enum class BuggedCrispyFishRoundingParams(val input: String, val expected: String) {
        FLOORED_UPPER_BOUND(input = "30.000959", expected = "30.000"),
        CEILED_LOWER_BOUND(input = "30.000960", expected = "30.001"),
        CEILED_FORMER_LOWER_BOUND(input = "30.000970", expected = "30.001"),
        ROUND_DOWN_HALF(input = "30.000500", expected = "30.000"),
        ALREADY_SCALED(input = "30.000", expected = "30.000"),
        ISSUE_78(input = "28.001962", expected = "28.002")
    }

    @ParameterizedTest
    @EnumSource
    fun `It should implement known bugged crispy fish rounding`(params: BuggedCrispyFishRoundingParams) {
        val input = BigDecimal(params.input)
        val factory = LegacyBuggedPaxTimeRunScoreFactory(StandardPenaltyFactory(TestPolicies.lsccV1))

        val actual = with (factory) {
            input.setScaleWithBuggedCrispyFishRounding()
        }

        val expected = BigDecimal(params.expected)
        assertThat(actual, params.name).isEqualTo(expected)
    }

    // https://github.com/coner-tech/coner-trailer/issues/78
    @Test
    fun `It should correctly score LSCC 2022 points 3 pax result position 60`() {
        val event = TestEvents.Lscc2022.points3
        val run = testRun(
            sequence = 467,
            participant = TestParticipants.Lscc2022Points3.CLAIRE_DICKERSON,
            time = Time("33.778"),
        )
        val factory = LegacyBuggedPaxTimeRunScoreFactory(
            penaltyFactory = StandardPenaltyFactory(event.policy)
        )

        val actual = factory.score(run)

        assertThat(actual).hasValue("28.002")
    }
}