package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.*
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.MockkEventResultsFixture
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.render.json.eventresults.JsonOverallEventResultsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.TextOverallEventResultsViewRenderer
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class EventResultsCommandTest : BaseDataSessionCommandTest<EventResultsCommand>() {

    override val di = DI.lazy {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        extend(super.di)
        import(mockkEventResultsFixture.module)
        bindSingleton<EventResultsViewRenderer<OverallEventResults>>(Format.JSON) { jsonOverallEventResultsViewRenderer }
        bindSingleton<EventResultsViewRenderer<OverallEventResults>>(Format.TEXT) { textOverallEventResultsViewRenderer }
    }

    private val eventService: EventService by lazy { command.dataSessionContainer.eventService }
    private val eventContextService: EventContextService by lazy { command.dataSessionContainer.eventContextService }
    private val mockkEventResultsFixture = MockkEventResultsFixture()
    private val jsonOverallEventResultsViewRenderer: JsonOverallEventResultsViewRenderer = mockk()
    private val textOverallEventResultsViewRenderer: TextOverallEventResultsViewRenderer = mockk()
    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    lateinit var crispyFishOverallEventResultsCreatorFactorySlot: CapturingSlot<EventResultsType>

    override fun createCommand(di: DI, global: GlobalModel) = EventResultsCommand(di, global)

    @Test
    fun `It should print results as json to console`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val event = eventContext.event
        val rawCalculator = mockkEventResultsFixture.rawCalculator
        coEvery { eventService.findByKey(event.id) } returns Result.success(event)
        coEvery { eventContextService.load(event) } returns Result.success(eventContext)
        val results = TestOverallRawEventResults.Lscc2019Simplified.points1
        every { rawCalculator.calculate() } returns results
        val render = "json"
        every { jsonOverallEventResultsViewRenderer(results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            rawCalculator.calculate()
            jsonOverallEventResultsViewRenderer(results)
        }
    }

    @Test
    fun `It should print results as plain text to console`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        val event = eventContext.event
        val rawCalculator = mockkEventResultsFixture.rawCalculator
        coEvery { eventService.findByKey(event.id) } returns Result.success(event)
        coEvery { eventContextService.load(event) } returns Result.success(eventContext)
        val results = TestOverallRawEventResults.Lscc2019Simplified.points1
        every { rawCalculator.calculate() } returns results
        val render = "plain text"
        every { textOverallEventResultsViewRenderer(results) } returns render

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
            textOverallEventResultsViewRenderer(results)
        }
    }
}