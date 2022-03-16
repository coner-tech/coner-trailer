package org.coner.trailer.cli.command.event

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.di.DataSessionScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.eventresults.EventResultsService
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.eventresults.StandardEventResultsTypes
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.util.FileOutputDestinationResolver
import org.coner.trailer.render.*
import org.coner.trailer.render.html.HtmlGroupEventResultsRenderer
import org.coner.trailer.render.html.HtmlOverallEventResultsRenderer
import org.coner.trailer.render.json.JsonGroupEventResultsRenderer
import org.coner.trailer.render.json.JsonIndividualEventResultsRenderer
import org.coner.trailer.render.json.JsonOverallEventResultsRenderer
import org.coner.trailer.render.text.TextGroupEventResultsRenderer
import org.coner.trailer.render.text.TextOverallEventResultsRenderer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path
import kotlin.io.path.readText

@ExtendWith(MockKExtension::class)
class EventResultsCommandTest : DIAware {

    lateinit var command: EventResultsCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bind { scoped(DataSessionScope).singleton { eventResultsService } }
        bind<OverallEventResultsRenderer<String, *>>(Format.JSON) with multiton { columns: List<EventResultsColumn> -> jsonOverallEventResultsRenderer }
        bind<OverallEventResultsRenderer<String, *>>(Format.TEXT) with multiton { columns: List<EventResultsColumn> -> textOverallEventResultsRenderer }
        bind<OverallEventResultsRenderer<String, *>>(Format.HTML) with multiton { columns: List<EventResultsColumn> -> htmlOverallEventResultsRenderer }
        bind<GroupEventResultsRenderer<String, *>>(Format.JSON) with multiton { columns: List<EventResultsColumn> -> jsonGroupEventResultsRenderer }
        bind<GroupEventResultsRenderer<String, *>>(Format.TEXT) with multiton { columns: List<EventResultsColumn> -> textGroupEventResultsRenderer }
        bind<GroupEventResultsRenderer<String, *>>(Format.HTML) with multiton { columns: List<EventResultsColumn> -> htmlGroupEventResultsRenderer }
        bind<IndividualEventResultsRenderer<String, *>>(Format.JSON) with instance(jsonIndividualEventResultsRenderer)
        bindInstance { fileOutputResolver }
    }
    override val diContext = diContext { command.diContext.value }

    private val eventService: EventService by instance()
    @MockK lateinit var eventResultsService: EventResultsService
    @MockK lateinit var jsonOverallEventResultsRenderer: JsonOverallEventResultsRenderer
    @MockK lateinit var textOverallEventResultsRenderer: TextOverallEventResultsRenderer
    @MockK lateinit var htmlOverallEventResultsRenderer: HtmlOverallEventResultsRenderer
    @MockK lateinit var jsonGroupEventResultsRenderer: JsonGroupEventResultsRenderer
    @MockK lateinit var textGroupEventResultsRenderer: TextGroupEventResultsRenderer
    @MockK lateinit var htmlGroupEventResultsRenderer: HtmlGroupEventResultsRenderer
    @MockK lateinit var jsonIndividualEventResultsRenderer: JsonIndividualEventResultsRenderer
    @MockK lateinit var fileOutputResolver: FileOutputDestinationResolver

    lateinit var crispyFishOverallEventResultsCreatorFactorySlot: CapturingSlot<EventResultsType>

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        crispyFishOverallEventResultsCreatorFactorySlot = slot()
        command = EventResultsCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should print results as json to console`() {
        val event = TestEvents.Lscc2019.points1
        every { eventService.findById(event.id) } returns event
        val results = mockk<OverallEventResults>()
        every { eventResultsService.buildOverallTypeResults(any(), any()) } returns results
        val render = "json"
        every { jsonOverallEventResultsRenderer.render(event, results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        verifySequence {
            eventService.findById(event.id)
            eventResultsService.buildOverallTypeResults(event, StandardEventResultsTypes.raw)
            jsonOverallEventResultsRenderer.render(event, results)
        }
    }

    @Test
    fun `It should print results as plain text to console`() {
        val event = TestEvents.Lscc2019.points1
        every { eventService.findById(event.id) } returns event
        val results = mockk<OverallEventResults>()
        every { eventResultsService.buildOverallTypeResults(any(), any()) } returns results
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
            eventResultsService.buildOverallTypeResults(event, StandardEventResultsTypes.raw)
            textOverallEventResultsRenderer.render(event, results)
        }
    }

    @Test
    fun `It should write results as html to file`(
        @TempDir output: Path
    ) {
        val event = TestEvents.Lscc2019.points1
        every { eventService.findById(event.id) } returns event
        val results = mockk<OverallEventResults>()
        every { eventResultsService.buildOverallTypeResults(any(), any()) } returns results
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
            eventResultsService.buildOverallTypeResults(event, StandardEventResultsTypes.pax)
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