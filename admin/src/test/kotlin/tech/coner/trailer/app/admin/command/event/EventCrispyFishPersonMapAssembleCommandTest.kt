package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import kotlinx.coroutines.launch
import org.awaitility.Awaitility
import org.junit.jupiter.api.Test
import org.kodein.di.*
import tech.coner.crispyfish.model.Signage
import tech.coner.trailer.Event
import tech.coner.trailer.Person
import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.command.event.crispyfish.EventCrispyFishPersonMapAssembleCommand
import tech.coner.trailer.app.admin.view.CrispyFishRegistrationView
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.crispyfish.TestRegistrations
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import java.nio.file.Paths

class EventCrispyFishPersonMapAssembleCommandTest : BaseDataSessionCommandTest<EventCrispyFishPersonMapAssembleCommand>() {

    private val service: EventService by instance()
    private val personService: PersonService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val crispyFishClassingMapper: CrispyFishClassingMapper by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier by instance()
    private val crispyFishRegistrationView: CrispyFishRegistrationView by instance()
    private val personView: TextView<PersonDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<EventCrispyFishPersonMapAssembleCommand>()

    @Test
    fun `It should assemble person map in case of registration with club memberId null`() {
        val eventCrispyFish = Event.CrispyFishMetadata(
            eventControlFile = Paths.get("eventControlFile.ecf"),
            classDefinitionFile = Paths.get("classDefinitionFile.def"),
            peopleMap = emptyMap()
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        val unmappedClubMemberIdNull = TestRegistrations.unmappedClubMemberIdNull()
        val person = Person(
            clubMemberId = null,
            firstName = checkNotNull(unmappedClubMemberIdNull.firstName),
            lastName = checkNotNull(unmappedClubMemberIdNull.lastName),
            motorsportReg = null
        )
        every { personService.searchByNameFrom(unmappedClubMemberIdNull) } returns listOf(person)
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = emptyList(),
            allRegistrations = listOf(unmappedClubMemberIdNull),
            allRuns = emptyList(),
            staging = emptyList(),
            runCount = 0
        )
        coEvery { crispyFishEventMappingContextService.load(event, eventCrispyFish) } returns context
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        val callbackSlot = slot<EventCrispyFishPersonMapVerifier.Callback>()
        every {
            eventCrispyFishPersonMapVerifier.verify(
                event = event,
                context = context,
                callback = capture(callbackSlot)
            )
        } answers  {
            val callback = callbackSlot.captured
            launch { callback.onUnmappedClubMemberIdNull(unmappedClubMemberIdNull) }
        }
        val classing = checkNotNull(TestParticipants.Lscc2019Points1.REBECCA_JACKSON.signage?.classing)
        every { crispyFishClassingMapper.toCore(any(), unmappedClubMemberIdNull) } returns classing
        val crispyFishRegistrationViewRendered = "crispyFishRegistrationViewRendered"
        every { crispyFishRegistrationView.render(any()) } returns crispyFishRegistrationViewRendered
        coJustRun { service.update(any()) }

        val testResult = command.test(arrayOf("${event.id}"), stdin = "0")

        val update = event.copy(
            crispyFish = eventCrispyFish.copy(
                peopleMap = mapOf(
                    Event.CrispyFishMetadata.PeopleMapKey(
                        classing = classing,
                        number = checkNotNull(unmappedClubMemberIdNull.signage.number),
                        firstName = checkNotNull(unmappedClubMemberIdNull.firstName),
                        lastName = checkNotNull(unmappedClubMemberIdNull.lastName)
                    ) to person
                )
            )
        )
        coVerifySequence {
            service.findByKey(event.id)
            service.update(update)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().contains(crispyFishRegistrationViewRendered)
        }
    }

}

private fun TestRegistrations.mapped() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Mapped",
    lastName = "Mapped",
    signage = Signage(
        classing = null,
        number = "0"
    ),
    memberNumber = "Mapped"
)

private fun TestRegistrations.unmappedClubMemberIdNull() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdNull",
    signage = Signage(
        classing = null,
        number = "1"
    ),
    memberNumber = null
)

private fun TestRegistrations.unmappedClubMemberIdNotFound() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdNotFound",
    signage = tech.coner.crispyfish.model.Signage(
        classing = null,
        number = "2"
    ),
    memberNumber = "WillNotBeFound"
)

private fun TestRegistrations.unmappedClubMemberIdAmbiguous() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdAmbiguous",
    signage = tech.coner.crispyfish.model.Signage(
        classing = null,
        number = "3",
    ),
    memberNumber = "Ambiguous"
)

private fun TestRegistrations.unmappedClubMemberIdMatchButNameMismatch() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy(
    firstName = "Unmapped",
    lastName = "ClubMemberIdMatchButNameMismatch",
    signage = Signage(
        classing = null,
        number = "4",
    ),
    memberNumber = "Match"
)

private fun TestRegistrations.unmappedExactMatch() = TestRegistrations.Lscc2019Points1.REBECCA_JACKSON.copy()

