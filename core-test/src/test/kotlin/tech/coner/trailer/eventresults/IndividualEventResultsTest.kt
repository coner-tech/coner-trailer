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

        assertThat(actual).resultsByParticipant().hasSize(2)
    }
}
