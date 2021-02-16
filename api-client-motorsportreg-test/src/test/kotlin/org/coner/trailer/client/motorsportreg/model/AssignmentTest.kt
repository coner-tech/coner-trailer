package org.coner.trailer.client.motorsportreg.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class AssignmentTest {

    @Test
    fun `Its motorsportRegMemberId getter should parse motorsportRegMemberUri`() {
        val assignment = TestAssignments.TestEvent.REBECCA_JACKSON_DAY_1

        val actual = assignment.motorsportRegMemberId

        assertThat(actual).isEqualTo("rebecca-jackson-member-id")
    }
}