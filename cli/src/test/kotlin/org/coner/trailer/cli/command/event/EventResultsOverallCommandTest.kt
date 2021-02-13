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
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.cli.view.OverallResultsReportTableView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallHandicapTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.render.OverallResultsReportRenderer
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.readText

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventResultsOverallCommandTest {

    lateinit var command: EventResultsOverallCommand

    @MockK lateinit var eventService: EventService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var crispyFishOverallRawTimeResultsReportCreator: OverallRawTimeResultsReportCreator
    @MockK lateinit var crispyFishOverallHandicapTimeResultsReportCreator: OverallHandicapTimeResultsReportCreator
    @MockK lateinit var reportTableView: OverallResultsReportTableView
    @MockK lateinit var reportHtmlRenderer: OverallResultsReportRenderer
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
            bind<OverallResultsReportRenderer>() with instance(reportHtmlRenderer)
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
        ) } returns resultsReport
        val render = "plain text"
        every { reportTableView.render(resultsReport) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--report", "crispy-fish-raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallRawTimeResultsReportCreator.createFromRegistrationData(
                eventCrispyFishMetadata = eventCrispyFish,
                context = context
            )
            reportTableView.render(resultsReport)
        }
    }

    @Test
    fun `It should write report as html to file`(
        @TempDir output: Path
    ) {
        val eventCrispyFish: Event.CrispyFishMetadata = mockk()
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = eventCrispyFish
        )
        every { eventService.findById(event.id) } returns event
        val resultsReport = mockk<OverallResultsReport>()
        val context = mockk<CrispyFishEventMappingContext>()
        every { crispyFishEventMappingContextService.load(eventCrispyFish) } returns context
        every { crispyFishOverallHandicapTimeResultsReportCreator.createFromRegistrationData(
            eventCrispyFishMetadata = eventCrispyFish,
            context = context
        ) } returns resultsReport
        val render = "<html>"
        every { reportHtmlRenderer.render(resultsReport) } returns render
        val actualDestination = output.resolve("handicap.html")
        every { fileOutputResolver.forEventResults(
            event = event,
            type = StandardResultsTypes.pax,
            defaultExtension = "html",
            path = output
        ) } returns actualDestination

        command.parse(arrayOf(
            "${event.id}",
            "--report", "crispy-fish-handicap",
            "--format", "html",
            "--file", "--destination", "$output"
        ))

        assertAll {
            assertThat(testConsole.output, "console output").isEmpty()
            assertThat(actualDestination.readText(), "file content").isEqualTo(render)
        }
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallHandicapTimeResultsReportCreator.createFromRegistrationData(
                eventCrispyFishMetadata = eventCrispyFish,
                context = context
            )
            reportHtmlRenderer.render(resultsReport)
            fileOutputResolver.forEventResults(
                event = event,
                type = StandardResultsTypes.pax,
                defaultExtension = "html",
                path = output
            )
        }
    }
}