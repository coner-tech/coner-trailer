package org.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import org.coner.trailer.Event
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.cli.view.OverallResultsReportTableView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallHandicapTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.eventresults.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventResultsOverallCommandTest {

    lateinit var command: EventResultsOverallCommand

    @MockK lateinit var eventService: EventService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var crispyFishOverallRawTimeResultsReportCreator: OverallRawTimeResultsReportCreator
    @MockK lateinit var crispyFishOverallHandicapTimeResultsReportCreator: OverallHandicapTimeResultsReportCreator
    @MockK lateinit var reportTableView: OverallResultsReportTableView
    @MockK lateinit var reportHtmlRenderer: KotlinxHtmlOverallResultsReportRenderer
    @MockK lateinit var fileOutputResolver: FileOutputDestinationResolver

    lateinit var crispyFishOverallResultsReportCreatorFactorySlot: CapturingSlot<ResultsType>
    lateinit var testConsole: StringBufferConsole
    
    @BeforeEach
    fun before() {
        crispyFishOverallResultsReportCreatorFactorySlot = slot()
        testConsole = StringBufferConsole()
        command = EventResultsOverallCommand(DI {
            bind<EventService>() with instance(eventService)
            bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
            bind<OverallRawTimeResultsReportCreator>() with instance(crispyFishOverallRawTimeResultsReportCreator)
            bind<OverallHandicapTimeResultsReportCreator>() with instance(crispyFishOverallHandicapTimeResultsReportCreator)
            bind<OverallResultsReportTableView>() with instance(reportTableView)
            bind<KotlinxHtmlOverallResultsReportRenderer>() with instance(reportHtmlRenderer)
            bind<FileOutputDestinationResolver>() with instance(fileOutputResolver)
        }).context {
            console = testConsole
        }
    }

    @Test
    fun `It should print report as plain text to console`() {
        val eventCrispyFish: Event.CrispyFishMetadata = mockk()
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { eventService.findById(event.id) } returns event
        val resultsReport = mockk<OverallResultsReport>()
        val context = mockk<CrispyFishEventMappingContext>()
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        every { crispyFishOverallRawTimeResultsReportCreator.createFromRegistrationData(
            eventCrispyFishMetadata = eventCrispyFish,
            context = context
        ) }
        val render = "plain text"
        every { reportTableView.render(resultsReport) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--report", "crispy-fish-raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
    }

    @Test
    fun `It should write report as html to file`() {
        TODO()
    }
}