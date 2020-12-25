package org.coner.trailer.datasource.crispyfish

import assertk.all
import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.*
import org.coner.trailer.datasource.crispyfish.fixture.SeasonFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CrispyFishParticipantMapperTest {

    lateinit var mapper: CrispyFishParticipantMapper

    lateinit var context: CrispyFishEventMappingContext

    @MockK
    private lateinit var crispyFishGroupingMapper: CrispyFishGroupingMapper

    @BeforeEach
    fun before() {
        mapper = CrispyFishParticipantMapper(crispyFishGroupingMapper)
        context = CrispyFishEventMappingContext(
            allClassDefinitions = SeasonFixture.Lscc2019Simplified.classDefinitions,
            allRegistrations = SeasonFixture.Lscc2019Simplified.event1.registrations(SeasonFixture.Lscc2019Simplified)
        )
    }

    @Test
    fun `It should map (core) Participant from (CF) Registration and (core) Person`() {
        val inputRegistration = TestRegistrations.Lscc2019Points1.BRANDY_HUFF
        val person = TestPeople.BRANDY_HUFF
        every {
            crispyFishGroupingMapper.toCore(
                context,
                inputRegistration
            )
        }.returns(TestParticipants.Lscc2019Points1.BRANDY_HUFF.signage.grouping)

        val actual = mapper.toCore(context, inputRegistration, person)

        assertThat(actual).isDataClassEqualTo(TestParticipants.Lscc2019Points1.BRANDY_HUFF)
    }

    @Test
    fun `It should map (core) Participant from (CF) Registration without (core) Person`() {
        val inputRegistration = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON
        val person = null
        every {
            crispyFishGroupingMapper.toCore(
                context,
                inputRegistration
            )
        }.returns(TestParticipants.Lscc2019Points1.REBECCA_JACKSON.signage.grouping)

        val actual = mapper.toCore(context, inputRegistration, person)

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