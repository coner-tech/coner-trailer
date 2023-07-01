package tech.coner.trailer

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ParticipantTest {

    @Test
    fun `Different participants should compare as not equal`() {
        val jackson = TestParticipants.Lifecycles.REBECCA_JACKSON
        val mckenzie = TestParticipants.Lifecycles.JIMMY_MCKENZIE

        val actual = jackson == mckenzie

        assertThat(actual).isFalse()
    }
}