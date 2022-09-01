package tech.coner.trailer.cli.command.event

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.di.*
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.render.EventResultsColumn
import tech.coner.trailer.render.ClazzEventResultsRenderer
import tech.coner.trailer.render.IndividualEventResultsRenderer
import tech.coner.trailer.render.OverallEventResultsRenderer
import tech.coner.trailer.render.html.HtmlClazzEventResultsRenderer
import tech.coner.trailer.render.html.HtmlOverallEventResultsRenderer
import tech.coner.trailer.render.json.JsonClazzEventResultsRenderer
import tech.coner.trailer.render.json.JsonIndividualEventResultsRenderer
import tech.coner.trailer.render.json.JsonOverallEventResultsRenderer
import tech.coner.trailer.render.text.TextClazzEventResultsRenderer
import tech.coner.trailer.render.text.TextOverallEventResultsRenderer
import java.nio.file.Path
import kotlin.io.path.readText

@ExtendWith(MockKExtension::class)
class EventResultsCommandTest : DIAware {

    lateinit var command: EventResultsCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkEventResultsFixture.module)
        import(mockkDatabaseModule())
        bind<OverallEventResultsRenderer<String, *>>(Format.JSON) with multiton { _: List<EventResultsColumn> -> jsonOverallEventResultsRenderer }
        bind<OverallEventResultsRenderer<String, *>>(Format.TEXT) with multiton { _: List<EventResultsColumn> -> textOverallEventResultsRenderer }
        bind<OverallEventResultsRenderer<String, *>>(Format.HTML) with multiton { _: List<EventResultsColumn> -> htmlOverallEventResultsRenderer }
        bind<ClazzEventResultsRenderer<String, *>>(Format.JSON) with multiton { _: List<EventResultsColumn> -> jsonGroupEventResultsRenderer }
        bind<ClazzEventResultsRenderer<String, *>>(Format.TEXT) with multiton { _: List<EventResultsColumn> -> textGroupEventResultsRenderer }
        bind<ClazzEventResultsRenderer<String, *>>(Format.HTML) with multiton { _: List<EventResultsColumn> -> htmlGroupEventResultsRenderer }
        bind<IndividualEventResultsRenderer<String, *>>(Format.JSON) with instance(jsonIndividualEventResultsRenderer)

        bindInstance { fileOutputResolver }
    }

    private val eventService: EventService by lazy { command.dataSessionContainer.eventService }
    private val eventContextService: EventContextService by lazy { command.dataSessionContainer.eventContextService }
    private val mockkEventResultsFixture = MockkEventResultsFixture()
    @MockK lateinit var jsonOverallEventResultsRenderer: JsonOverallEventResultsRenderer
    @MockK lateinit var textOverallEventResultsRenderer: TextOverallEventResultsRenderer
    @MockK lateinit var htmlOverallEventResultsRenderer: HtmlOverallEventResultsRenderer
    @MockK lateinit var jsonGroupEventResultsRenderer: JsonClazzEventResultsRenderer
    @MockK lateinit var textGroupEventResultsRenderer: TextClazzEventResultsRenderer
    @MockK lateinit var htmlGroupEventResultsRenderer: HtmlClazzEventResultsRenderer
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
        val eventContext = TestEventContexts.Lscc2019.points1
        val event = eventContext.event
        val rawCalculator = mockkEventResultsFixture.rawCalculator
        coEvery { eventService.findByKey(event.id) } returns Result.success(event)
        coEvery { eventContextService.load(event) } returns Result.success(eventContext)
        val results = mockk<OverallEventResults>()
        every { rawCalculator.calculate() } returns results
        val render = "json"
        every { jsonOverallEventResultsRenderer.render(event, results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            rawCalculator.calculate()
            jsonOverallEventResultsRenderer.render(event, results)
        }
    }

    @Test
    fun `It should print results as plain text to console`() {
        val eventContext = TestEventContexts.Lscc2019.points1
        val event = eventContext.event
        val rawCalculator = mockkEventResultsFixture.rawCalculator
        coEvery { eventService.findByKey(event.id) } returns Result.success(event)
        coEvery { eventContextService.load(event) } returns Result.success(eventContext)
        val results = mockk<OverallEventResults>()
        every { rawCalculator.calculate() } returns results
        val render = "plain text"
        every { textOverallEventResultsRenderer.render(event, results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw",
            "--format", "text"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            rawCalculator.calculate()
            textOverallEventResultsRenderer.render(event, results)
        }
    }

    @Test
    fun `It should write results as html to file`(
        @TempDir output: Path
    ) {
        val eventContext = TestEventContexts.Lscc2019.points1
        val event = eventContext.event
        val paxCalculator = mockkEventResultsFixture.paxCalculator
        coEvery { eventService.findByKey(event.id) } returns Result.success(event)
        coEvery { eventContextService.load(event) } returns Result.success(eventContext)
        val results = mockk<OverallEventResults>()
        every { paxCalculator.calculate() } returns results
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
            "--format", "html",
            "--file", "--file-output", "$output"
        ))

        assertAll {
            assertThat(testConsole.output, "console output").isEmpty()
            assertThat(actualDestination.readText(), "file content").isEqualTo(render)
        }
        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            paxCalculator.calculate()
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