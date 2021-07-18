package org.coner.trailer.cli.command.event

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.TestClasses
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.eventresults.OverallPaxTimeEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.OverallRawEventResultsFactory
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.StandardEventResultsTypes
import org.coner.trailer.io.service.CrispyFishClassService
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.render.EventResultsColumn
import org.coner.trailer.render.html.HtmlGroupedEventResultsRenderer
import org.coner.trailer.render.html.HtmlOverallEventResultsRenderer
import org.coner.trailer.render.json.JsonGroupedEventResultsRenderer
import org.coner.trailer.render.json.JsonOverallEventResultsRenderer
import org.coner.trailer.render.text.TextGroupedEventResultsRenderer
import org.coner.trailer.render.text.TextOverallEventResultsRenderer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.readText

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventResultsCommandTest {

    lateinit var command: EventResultsCommand

    @MockK lateinit var eventService: EventService
    @MockK lateinit var crispyFishClassService: CrispyFishClassService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var crispyFishOverallRawEventResultsFactory: OverallRawEventResultsFactory
    @MockK lateinit var crispyFishOverallPaxEventResultsFactory: OverallPaxTimeEventResultsFactory
    @MockK lateinit var jsonOverallEventResultsRenderer: JsonOverallEventResultsRenderer
    @MockK lateinit var jsonGroupedEventResultsRenderer: JsonGroupedEventResultsRenderer
    @MockK lateinit var textOverallEventResultsRenderer: TextOverallEventResultsRenderer
    @MockK lateinit var textGroupedEventResultsRenderer: TextGroupedEventResultsRenderer
    @MockK lateinit var htmlOverallEventResultsRenderer: HtmlOverallEventResultsRenderer
    @MockK lateinit var htmlGroupedEventResultsRenderer: HtmlGroupedEventResultsRenderer
    @MockK lateinit var fileOutputResolver: FileOutputDestinationResolver

    lateinit var crispyFishOverallEventResultsCreatorFactorySlot: CapturingSlot<EventResultsType>
    lateinit var testConsole: StringBufferConsole
    
    @BeforeEach
    fun before() {
        crispyFishOverallEventResultsCreatorFactorySlot = slot()
        testConsole = StringBufferConsole()
        command = EventResultsCommand(DI {
            bind<EventService>() with instance(eventService)
            bind<CrispyFishClassService>() with instance(crispyFishClassService)
            bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
            bind<OverallRawEventResultsFactory>() with multiton { policy: Policy -> crispyFishOverallRawEventResultsFactory }
            bind<OverallPaxTimeEventResultsFactory>() with multiton { policy: Policy -> crispyFishOverallPaxEventResultsFactory }
            bind<JsonOverallEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> jsonOverallEventResultsRenderer }
            bind<JsonGroupedEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> jsonGroupedEventResultsRenderer }
            bind<TextOverallEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> textOverallEventResultsRenderer }
            bind<TextGroupedEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> textGroupedEventResultsRenderer }
            bind<HtmlOverallEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> htmlOverallEventResultsRenderer }
            bind<HtmlGroupedEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> htmlGroupedEventResultsRenderer }
            bind<FileOutputDestinationResolver>() with instance(fileOutputResolver)
        }).context {
            console = testConsole
        }
    }

    @Test
    fun `It should print results as json to console`() {
        val eventCrispyFish: Event.CrispyFishMetadata = mockk {
            every { classDefinitionFile } returns "classDefinitionFile"
        }
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { eventService.findById(event.id) } returns event
        val results = mockk<OverallEventResults>()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        val context = mockk<CrispyFishEventMappingContext>()
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        every { crispyFishOverallRawEventResultsFactory.factory(
            eventCrispyFishMetadata = eventCrispyFish,
            allClassesByAbbreviation = any(),
            context = context
        ) } returns results
        val render = "json"
        every { jsonOverallEventResultsRenderer.render(event, results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallRawEventResultsFactory.factory(
                eventCrispyFishMetadata = eventCrispyFish,
                allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
                context = context
            )
            jsonOverallEventResultsRenderer.render(event, results)
        }
    }

    @Test
    fun `It should print results as plain text to console`() {
        val eventCrispyFish: Event.CrispyFishMetadata = mockk {
            every { classDefinitionFile } returns "classDefinitionFile"
        }
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { eventService.findById(event.id) } returns event
        val results = mockk<OverallEventResults>()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        val context = mockk<CrispyFishEventMappingContext>()
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        every { crispyFishOverallRawEventResultsFactory.factory(
            eventCrispyFishMetadata = eventCrispyFish,
            allClassesByAbbreviation = any(),
            context = context
        ) } returns results
        val render = "plain text"
        every { textOverallEventResultsRenderer.render(event, results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw",
            "--text"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallRawEventResultsFactory.factory(
                eventCrispyFishMetadata = eventCrispyFish,
                allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
                context = context
            )
            textOverallEventResultsRenderer.render(event, results)
        }
    }

    @Test
    fun `It should write results as html to file`(
        @TempDir output: Path
    ) {
        val eventCrispyFish: Event.CrispyFishMetadata = mockk {
            every { classDefinitionFile } returns "classDefinitionFile"
        }
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { eventService.findById(event.id) } returns event
        val results = mockk<OverallEventResults>()
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        val context = mockk<CrispyFishEventMappingContext>()
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        every { crispyFishOverallPaxEventResultsFactory.factory(
            eventCrispyFishMetadata = eventCrispyFish,
            allClassesByAbbreviation = any(),
            context = context
        ) } returns results
        val render = "<html>"
        every { htmlOverallEventResultsRenderer.render(event, results) } returns render
        val actualDestination = output.resolve("pax.html")
        every { fileOutputResolver.forEventResults(
            event = event,
            type = StandardEventResultsTypes.pax,
            defaultExtension = "html",
            path = output
        ) } returns actualDestination

        command.parse(arrayOf(
            "${event.id}",
            "--type", "pax",
            "--html",
            "--file", "--file-output", "$output"
        ))

        assertAll {
            assertThat(testConsole.output, "console output").isEmpty()
            assertThat(actualDestination.readText(), "file content").isEqualTo(render)
        }
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallPaxEventResultsFactory.factory(
                eventCrispyFishMetadata = eventCrispyFish,
                allClassesByAbbreviation = TestClasses.Lscc2019.allByAbbreviation,
                context = context
            )
            htmlOverallEventResultsRenderer.render(event, results)
            fileOutputResolver.forEventResults(
                event = event,
                type = StandardEventResultsTypes.pax,
                defaultExtension = "html",
                path = output
            )
        }
    }
}