package org.coner.trailer.cli.command.event

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.coner.crispyfish.model.Registration
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.view.CrispyFishRegistrationView
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.datasource.crispyfish.TestRegistrations
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.verification.EventCrispyFishPersonMapVerifier
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapAssembleCommandTest {
    
    lateinit var command: EventCrispyFishPersonMapAssembleCommand
    
    @MockK lateinit var service: EventService
    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier
    @MockK lateinit var crispyFishRegistrationView: CrispyFishRegistrationView
    @MockK lateinit var personView: PersonView
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper
    @MockK lateinit var crispyFishPersonMapper: CrispyFishPersonMapper
    
    @BeforeEach
    fun before() {
        command = EventCrispyFishPersonMapAssembleCommand(
            di = DI {
                bind<EventService>() with instance(service)
                bind<PersonService>() with instance(personService)
                bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
                bind<EventCrispyFishPersonMapVerifier>() with instance(eventCrispyFishPersonMapVerifier)
                bind<CrispyFishRegistrationView>() with instance(crispyFishRegistrationView)
                bind<PersonView>() with instance(personView)
                bind<CrispyFishParticipantMapper>() with instance(crispyFishParticipantMapper)
                bind<CrispyFishPersonMapper>() with instance(crispyFishPersonMapper)
            }
        )
    }

    @Test
    fun `It should assemble person map`() {
        val eventCrispyFish = requireNotNull(TestEvents.Lscc2019.points1.crispyFish).copy(
            peopleMap = emptyMap()
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { service.findById(event.id) } returns event
        val unmappedClubMemberIdNull = TestRegistrations.unmappedClubMemberIdNull()
        val unmappedClubMemberIdNotFound = TestRegistrations.unmappedClubMemberIdNotFound()
        val unmappedClubMemberIdAmbiguous = TestRegistrations.unmappedClubMemberIdAmbiguous()
        val unmappedClubMemberIdMatchButNameMismatch = TestRegistrations.unmappedClubMemberIdMatchButNameMismatch()
        val unmappedExactMatch = TestRegistrations.unmappedExactMatch()
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = mockk(),
            allRegistrations = listOf(
                unmappedClubMemberIdNull,
                unmappedClubMemberIdNotFound,
                unmappedClubMemberIdAmbiguous,
                unmappedClubMemberIdMatchButNameMismatch,
                unmappedExactMatch
            )
        )
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        TODO("Lots of mocking left to do")

        command.parse(arrayOf("${event.id}"))


    }

}

private fun TestRegistrations.mapped() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Mapped",
    lastName = "Mapped",
    number = "0",
    memberNumber = "Mapped"
)

private fun TestRegistrations.unmappedClubMemberIdNull() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdNull",
    number = "1",
    memberNumber = null
)

private fun TestRegistrations.unmappedClubMemberIdNotFound() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdNotFound",
    number = "2",
    memberNumber = "WillNotBeFound"
)

private fun TestRegistrations.unmappedClubMemberIdAmbiguous() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdAmbiguous",
    number = "3",
    memberNumber = "Ambiguous"
)

private fun TestRegistrations.unmappedClubMemberIdMatchButNameMismatch() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdMatchButNameMismatch",
    number = "4",
    memberNumber = "Match"
)

private fun TestRegistrations.unmappedExactMatch() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy()

