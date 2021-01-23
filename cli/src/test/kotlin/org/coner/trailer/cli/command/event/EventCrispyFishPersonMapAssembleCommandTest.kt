package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktConsole
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import org.awaitility.Awaitility
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.TestEvents
import org.coner.trailer.TestPeople
import org.coner.trailer.cli.clikt.StringBufferConsole
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
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper
    @MockK lateinit var crispyFishPersonMapper: CrispyFishPersonMapper

    lateinit var useConsole: StringBufferConsole
    
    @BeforeEach
    fun before() {
        useConsole = StringBufferConsole()
        command = EventCrispyFishPersonMapAssembleCommand(
            di = DI {
                bind<EventService>() with instance(service)
                bind<PersonService>() with instance(personService)
                bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
                bind<EventCrispyFishPersonMapVerifier>() with instance(eventCrispyFishPersonMapVerifier)
                bind<CrispyFishRegistrationView>() with instance(CrispyFishRegistrationView())
                bind<PersonView>() with instance(PersonView(useConsole))
                bind<CrispyFishParticipantMapper>() with instance(crispyFishParticipantMapper)
                bind<CrispyFishPersonMapper>() with instance(crispyFishPersonMapper)
            }
        ).context {
            console = useConsole
        }
    }

    @Test
    fun `It should assemble person map in case of registration with club memberId null`() {
        val eventCrispyFish = Event.CrispyFishMetadata(
            eventControlFile = "eventControlFile.ecf",
            classDefinitionFile = "classDefinitionFile.def",
            peopleMap = emptyMap()
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { service.findById(event.id) } returns event
        val unmappedClubMemberIdNull = TestRegistrations.unmappedClubMemberIdNull()
        val person = Person(
            clubMemberId = null,
            firstName = unmappedClubMemberIdNull.firstName,
            lastName = unmappedClubMemberIdNull.lastName,
            motorsportReg = null
        )
        every { personService.searchByNameFrom(unmappedClubMemberIdNull) } returns listOf(person)
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = emptyList(),
            allRegistrations = listOf(unmappedClubMemberIdNull)
        )
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        val callbackSlot = slot<EventCrispyFishPersonMapVerifier.Callback>()
        every {
            eventCrispyFishPersonMapVerifier.verify(context, eventCrispyFish.peopleMap, capture(callbackSlot))
        } answers {
            val callback = callbackSlot.captured
//            callback.onUnmappedClubMemberIdNull(unmappedClubMemberIdNull)
//            Awaitility.await()
//                .until { useConsole.output.endsWith("> ") }
//            useConsole.writeInput("0")
        }

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

