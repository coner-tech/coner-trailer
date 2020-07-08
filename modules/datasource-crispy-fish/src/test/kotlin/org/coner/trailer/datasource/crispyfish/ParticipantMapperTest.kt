package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.coner.trailer.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ParticipantMapperTest {

    @BeforeEach
    fun before() {
        mockkObject(GroupingMapper)
    }

    @AfterEach
    fun after() {
        unmockkAll()
    }

    @Test
    fun `It should map (core) Participant from (CF) Registration and (core) Person`() {
        val inputRegistration = TestRegistrations.Lscc2019Points1.BRANDY_HUFF
        val person = TestPeople.BRANDY_HUFF
        every { GroupingMapper.map(inputRegistration) }.returns(TestParticipants.Lscc2019Points1.BRANDY_HUFF.grouping)

        val actual = ParticipantMapper.map(inputRegistration, person)

        assertThat(actual).isDataClassEqualTo(TestParticipants.Lscc2019Points1.BRANDY_HUFF)
    }

    @Test
    fun `It should map (core) Participant from (CF) Registration without (core) Person`() {
        val inputRegistration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val person = null
        every { GroupingMapper.map(inputRegistration) }.returns(TestParticipants.Lscc2019Points1.REBECCA_JACKSON.grouping)

        val actual = ParticipantMapper.map(inputRegistration, person)

        assertThat(actual).all {
            isEqualToIgnoringGivenProperties(
                    TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
                    Participant::person,
                    Participant::seasonPointsEligible
            )
            doesNotHavePerson()
            isNotSeasonPointsEligible()
        }
    }

}