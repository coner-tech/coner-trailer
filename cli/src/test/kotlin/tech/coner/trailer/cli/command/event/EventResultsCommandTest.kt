package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindInstance
import org.kodein.di.bindSingleton
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.di.Format
import tech.coner.trailer.di.MockkEventResultsFixture
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
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

@ExtendWith(MockKExtension::class)
class EventResultsCommandTest : DIAware {

    lateinit var command: EventResultsCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkEventResultsFixture.module)
        import(mockkDatabaseModule())
        bindSingleton<OverallEventResultsRenderer<String, *>>(Format.JSON) { jsonOverallEventResultsRenderer }
        bindSingleton<OverallEventResultsRenderer<String, *>>(Format.TEXT) { textOverallEventResultsRenderer }
        bindSingleton<ClazzEventResultsRenderer<String, *>>(Format.JSON) { jsonGroupEventResultsRenderer }
        bindSingleton<ClazzEventResultsRenderer<String, *>>(Format.TEXT) { textGroupEventResultsRenderer }
        bindSingleton<IndividualEventResultsRenderer<String, *>>(Format.JSON) { jsonIndividualEventResultsRenderer }

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
        every { jsonOverallEventResultsRenderer.render(eventContext, results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            rawCalculator.calculate()
            jsonOverallEventResultsRenderer.render(eventContext, results)
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
        every { textOverallEventResultsRenderer.render(eventContext, results) } returns render

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
            textOverallEventResultsRenderer.render(eventContext, results)
        }
    }
}