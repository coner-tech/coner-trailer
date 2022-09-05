package tech.coner.trailer.eventresults

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isNotEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tech.coner.trailer.TestParticipants

class IndividualEventResultsTest {

    @Test
    fun `It should hold some participants with some runs`() {
        val actual = TestIndividualEventResults.Lifecycles.Create.someParticipantsWithSomeRuns

        assertThat(actual).allByParticipant().hasSize(2)
    }

    @Nested
    inner class AllByParticipantComparatorTest {

        @Test
        fun `It should compare participants by last name first`() {
            val jackson = TestParticipants.Lifecycles.REBECCA_JACKSON
            val mckenzie = TestParticipants.Lifecycles.JIMMY_MCKENZIE

            val actual = IndividualEventResults.Comparators.allByParticipant.compare(mckenzie, jackson)

            assertThat(actual).isNotEqualTo(0)
        }
    }
}