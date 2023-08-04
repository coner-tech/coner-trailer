package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.*
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.event.crispyfish.EventCrispyFishPersonMapAddCommand
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import java.nio.file.Paths

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapAddCommandTest : BaseDataSessionCommandTest<EventCrispyFishPersonMapAddCommand>() {

    private val service: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val personService: PersonService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val view: TextView<EventDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<EventCrispyFishPersonMapAddCommand>()

    @Test
    fun `It should add a force person`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val crispyFish = Event.CrispyFishMetadata(
            eventControlFile = Paths.get("irrelevant"),
            classDefinitionFile = Paths.get("irrelevant"),
            peopleMap = emptyMap()
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = crispyFish
        )
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
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { personService.findById(person.id) } returns person
        val set = event.copy(
            crispyFish = crispyFish.copy(
                peopleMap = mapOf(key to person)
            )
        )
        coEvery { crispyFishEventMappingContextService.load(set.crispyFish!!) } returns context
        coJustRun { service.update(set) }
        val viewRender = "view rendered"
//        every { view(set) } returns viewRender

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
            crispyFishClassService.loadAllByAbbreviation(any())
            personService.findById(person.id)
            service.update(set)
//            view(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRender)
    }
}