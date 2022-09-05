package tech.coner.trailer

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ParticipantTest {

    enum class SignageStringParam(val participant: Participant, val expectedClassingNumber: String?, val expectedNumberClassing: String?) {
        OPEN_CLASS_PARTICIPANT(TestParticipants.Lscc2019Points1.REBECCA_JACKSON, "HS 1", "1 HS"),
        NOVICE_CLASS_PARTICIPANT(TestParticipants.Lscc2019Points1.BRANDY_HUFF, "NOV BS 177", "177 NOV BS")
    }

    @ParameterizedTest
    @EnumSource
    fun `It should produce correct signage strings`(param: SignageStringParam) {
        val signage = param.participant.signage

        assertThat(signage?.classingNumber).isEqualTo(param.expectedClassingNumber)
        assertThat(signage?.numberClassing).isEqualTo(param.expectedNumberClassing)
    }

    @Test
    fun `Different participants should compare as not equal`() {
        val jackson = TestParticipants.LifecycleCases.REBECCA_JACKSON
        val mckenzie = TestParticipants.LifecycleCases.JIMMY_MCKENZIE

        val actual = jackson == mckenzie

        assertThat(actual).isFalse()
    }
}