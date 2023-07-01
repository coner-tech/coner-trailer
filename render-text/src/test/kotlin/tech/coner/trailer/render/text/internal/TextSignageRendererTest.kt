package tech.coner.trailer.render.text.internal

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import tech.coner.trailer.*

class TextSignageRendererTest {

    companion object {
        val policyWithSignageStyleClassingNumber = TestPolicies.standardTest
        val policyWithSignageStyleNumberClassing = TestPolicies.standardWithSignageStyleNumberClassing
        @JvmStatic
        @BeforeAll
        fun sanityChecks() {
            assertAll {
                assertThat(policyWithSignageStyleClassingNumber).signageStyle().isEqualTo(SignageStyle.CLASSING_NUMBER)
                assertThat(policyWithSignageStyleNumberClassing).signageStyle().isEqualTo(SignageStyle.NUMBER_CLASSING)
            }
        }
    }


    enum class SignageStringParam(
        val policy: Policy,
        val participant: Participant,
        val expected: String?,
    ) {
        OPEN_CLASS_PARTICIPANT_CLASSING_NUMBER(
            policy = policyWithSignageStyleClassingNumber,
            participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            expected = "HS 1"
        ),
        NOVICE_CLASS_PARTICIPANT_CLASSING_NUMBER(
            policy = policyWithSignageStyleClassingNumber,
            participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
            expected = "NOV BS 177"
        ),
        OPEN_CLASS_PARTICIPANT_NUMBER_CLASSING (
            policy = policyWithSignageStyleNumberClassing,
            participant = TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            expected = "1 HS"
        ),
        NOVICE_CLASS_PARTICIPANT_NUMBER_CLASSING(
            policy = policyWithSignageStyleNumberClassing,
            participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
            expected = "177 NOV BS"
        )
    }

    @ParameterizedTest
    @EnumSource
    fun `It should render signage correctly`(param: SignageStringParam) {
        val renderer = TextSignageRenderer(param.policy)

        val actual = renderer(param.participant.signage)

        assertThat(actual).isEqualTo(param.expected)
    }

}