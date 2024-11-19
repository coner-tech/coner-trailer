package tech.coner.trailer.domain.entity

import assertk.assertThat
import assertk.assertions.isFalse
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestParticipants

class ParticipantTest {

    @Test
    fun `Different participants should compare as not equal`() {
        val jackson = TestParticipants.Lifecycles.REBECCA_JACKSON
        val mckenzie = TestParticipants.Lifecycles.JIMMY_MCKENZIE

        val actual = jackson == mckenzie

        assertThat(actual).isFalse()
    }
}