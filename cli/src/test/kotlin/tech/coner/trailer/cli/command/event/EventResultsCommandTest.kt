package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.Format
import tech.coner.trailer.di.MockkEventResultsFixture
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.eventresults.TestOverallRawEventResults
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.render.eventresults.OverallEventResultsRenderer
import tech.coner.trailer.render.json.eventresults.JsonOverallEventResultsRenderer
import tech.coner.trailer.render.text.eventresults.TextOverallEventResultsRenderer

class EventResultsCommandTest : BaseDataSessionCommandTest<EventResultsCommand>() {

    override val di = DI.lazy {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        extend(super.di)
        import(mockkEventResultsFixture.module)
        bindFactory<Policy, OverallEventResultsRenderer>(Format.JSON) { jsonOverallEventResultsRenderer }
        bindFactory<Policy, OverallEventResultsRenderer>(Format.TEXT) { textOverallEventResultsRenderer }
    }

    private val eventService: EventService by lazy { command.dataSessionContainer.eventService }
    private val eventContextService: EventContextService by lazy { command.dataSessionContainer.eventContextService }
    private val mockkEventResultsFixture = MockkEventResultsFixture()
    private val jsonOverallEventResultsRenderer: JsonOverallEventResultsRenderer = mockk()
    private val textOverallEventResultsRenderer: TextOverallEventResultsRenderer = mockk()
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
        every { jsonOverallEventResultsRenderer.render(results) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--type", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            rawCalculator.calculate()
            jsonOverallEventResultsRenderer.render(results)
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
        every { textOverallEventResultsRenderer.render(results) } returns render

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
            textOverallEventResultsRenderer.render(results)
        }
    }
}