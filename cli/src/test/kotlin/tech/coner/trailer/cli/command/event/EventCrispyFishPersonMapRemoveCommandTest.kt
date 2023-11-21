package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import java.nio.file.Paths
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Classing
import tech.coner.trailer.Event
import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.event.crispyfish.EventCrispyFishPersonMapRemoveCommand
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapRemoveCommandTest : BaseDataSessionCommandTest<EventCrispyFishPersonMapRemoveCommand>() {

    private val service: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val personService: PersonService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val adapter: Adapter<Event, EventDetailModel> by instance()
    private val view: TextView<EventDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<EventCrispyFishPersonMapRemoveCommand>()

    @Test
    fun `It should remove specific person from crispy fish person map`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val person = TestPeople.REBECCA_JACKSON
        val classing = Classing(
            group = null,
            handicap = TestClasses.Lscc2019.HS
        )
        val number = "1"
        val key = Event.CrispyFishMetadata.PeopleMapKey(
            classing = classing,
            number = number,
            firstName = person.firstName,
            lastName = person.lastName
        )
        val crispyFish = Event.CrispyFishMetadata(
            eventControlFile = Paths.get("irrelevant"),
            classDefinitionFile = Paths.get("irrelevant"),
            peopleMap = mapOf(key to person)
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = crispyFish
        )
        coEvery { service.findByKey(any()) } returns Result.success(event)
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { personService.findById(any()) } returns person
        val set = event.copy(
            crispyFish = crispyFish.copy(
                peopleMap = emptyMap()
            )
        )
        coEvery { crispyFishEventMappingContextService.load(any(), any()) } returns context
        coJustRun {service.update(any()) }
        val viewRender = "view rendered"
        val model: EventDetailModel = mockk()
        every { adapter(any()) } returns model
        every { view(any()) } returns viewRender

        command.parse(arrayOf(
            "${event.id}",
            "--handicap", classing.handicap.abbreviation,
            "--number", number,
            "--first-name", person.firstName,
            "--last-name", person.lastName,
            "--person-id", "${person.id}"
        ))

        coVerifySequence {
            service.findByKey(event.id)
            crispyFishClassService.loadAllByAbbreviation(crispyFish.classDefinitionFile)
            personService.findById(person.id)
            service.update(set)
            adapter(set)
            view(model)
        }
        assertThat(testConsole.output).isEqualTo(viewRender)
    }
}