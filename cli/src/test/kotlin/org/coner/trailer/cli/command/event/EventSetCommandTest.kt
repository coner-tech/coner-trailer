package org.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.Event
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.CrispyFishGroupingService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventSetCommandTest {

    lateinit var command: EventSetCommand

    @MockK lateinit var dbConfig: DatabaseConfiguration
    @MockK lateinit var service: EventService
    @MockK lateinit var groupingService: CrispyFishGroupingService
    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var view: EventView

    lateinit var testConsole: StringBufferConsole

    @TempDir lateinit var root: Path
    lateinit var crispyFish: Path

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        crispyFish = root.resolve("crispy-fish").createDirectory()
        command = EventSetCommand(
            di = DI {
                bind<DatabaseConfiguration>() with instance(dbConfig)
                bind<EventService>() with instance(service)
                bind<CrispyFishGroupingService>() with instance(groupingService)
                bind<PersonService>() with instance(personService)
                bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
                bind<EventView>() with instance(view)
            }
        ).apply {
            context {
                console = testConsole
            }
        }
    }

    @Test
    fun `It should set event properties`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val original = TestEvents.Lscc2019.points1
        val setCrispyFish = Event.CrispyFishMetadata(
            eventControlFile = "set-event-control-file.ecf",
            classDefinitionFile = "set-class-definition-file.ecf",
            peopleMap = emptyMap()
        )
        val set = original.copy(
            name = "It should set event properties",
            date = LocalDate.parse("2020-12-07"),
            crispyFish = setCrispyFish
        )
        val setEventControlFile = crispyFish.resolve(set.crispyFish!!.eventControlFile).createFile()
        val setClassDefinitionFile = crispyFish.resolve(set.crispyFish!!.classDefinitionFile).createFile()
        every { dbConfig.crispyFishDatabase } returns crispyFish
        every { service.findById(original.id) } returns original
        every { crispyFishEventMappingContextService.load(setCrispyFish) } returns context
        justRun { service.update(
            update = set,
            context = context,
            eventCrispyFishPersonMapVerifierCallback = null
        ) }
        val viewRendered = "view rendered set event named: ${set.name}"
        every { view.render(set) } returns viewRendered

        command.parse(arrayOf(
            "${original.id}",
            "--name", set.name,
            "--date", "${set.date}",
            "--crispy-fish", "set",
            "--event-control-file", "$setEventControlFile",
            "--class-definition-file", "$setClassDefinitionFile"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(
                update = set,
                context = context,
                eventCrispyFishPersonMapVerifierCallback = null
            )
            view.render(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should keep event properties for options not passed`() {
        val original = TestEvents.Lscc2019.points1
        every { service.findById(original.id) } returns original
        justRun {
            service.update(
                update = original,
                context = null,
                eventCrispyFishPersonMapVerifierCallback = null
            )
        }
        val viewRendered = "view rendered set event named: ${original.name}"
        every { view.render(eq(original)) } returns viewRendered

        command.parse(arrayOf(
            "${original.id}",
        ))

        verifySequence {
            service.findById(original.id)
            service.update(
                update = original,
                context = null,
                eventCrispyFishPersonMapVerifierCallback = null
            )
            view.render(original)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

}