package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.TestPolicies
import java.math.BigDecimal

class LegacyBuggedPaxTimeRunScoreFactoryTest {

    enum class BuggedCrispyFishRoundingParams(val input: String, val expected: String) {
        FLOORED_UPPER_BOUND(input = "30.000969", expected = "30.000"),
        CEILED_LOWER_BOUND(input = "30.000970", expected = "30.001"),
        ROUND_DOWN_HALF(input = "30.000500", expected = "30.000"),
        ALREADY_SCALED(input = "30.000", expected = "30.000")
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
}