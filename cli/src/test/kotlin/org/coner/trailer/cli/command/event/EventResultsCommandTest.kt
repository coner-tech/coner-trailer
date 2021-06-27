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
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallPaxTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.asciitable.AsciiTableGroupedResultsReportRenderer
import org.coner.trailer.render.asciitable.AsciiTableOverallResultsReportRenderer
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlGroupedResultsReportRenderer
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
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var crispyFishOverallRawTimeResultsReportCreator: OverallRawTimeResultsReportCreator
    @MockK lateinit var crispyFishOverallPaxTimeResultsReportCreator: OverallPaxTimeResultsReportCreator
    @MockK lateinit var asciiTableOverallResultsReportRenderer: AsciiTableOverallResultsReportRenderer
    @MockK lateinit var asciiTableGroupedResultsReportRenderer: AsciiTableGroupedResultsReportRenderer
    @MockK lateinit var kotlinxHtmlOverallResultsReportRenderer: KotlinxHtmlOverallResultsReportRenderer
    @MockK lateinit var kotlinxHtmlGroupedResultsReportRenderer: KotlinxHtmlGroupedResultsReportRenderer
    @MockK lateinit var fileOutputResolver: FileOutputDestinationResolver

    lateinit var crispyFishOverallResultsReportCreatorFactorySlot: CapturingSlot<ResultsType>
    lateinit var testConsole: StringBufferConsole
    
    @BeforeEach
    fun before() {
        crispyFishOverallResultsReportCreatorFactorySlot = slot()
        testConsole = StringBufferConsole()
        command = EventResultsCommand(DI {
            bind<EventService>() with instance(eventService)
            bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
            bind<OverallRawTimeResultsReportCreator>() with multiton { policy: Policy -> crispyFishOverallRawTimeResultsReportCreator }
            bind<OverallPaxTimeResultsReportCreator>() with multiton { policy: Policy -> crispyFishOverallPaxTimeResultsReportCreator }
            bind<AsciiTableOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> asciiTableOverallResultsReportRenderer }
            bind<AsciiTableGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> asciiTableGroupedResultsReportRenderer }
            bind<KotlinxHtmlOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> kotlinxHtmlOverallResultsReportRenderer }
            bind<KotlinxHtmlGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> kotlinxHtmlGroupedResultsReportRenderer }
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
        every { asciiTableOverallResultsReportRenderer.render(event, resultsReport) } returns render

        command.parse(arrayOf(
            "${event.id}",
            "--report", "raw"
        ))

        assertThat(testConsole.output).isEqualTo(render)
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallRawTimeResultsReportCreator.createFromRegistrationData(
                eventCrispyFishMetadata = eventCrispyFish,
                context = context
            )
            asciiTableOverallResultsReportRenderer.render(event, resultsReport)
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
        every { crispyFishOverallPaxTimeResultsReportCreator.createFromRegistrationData(
            eventCrispyFishMetadata = eventCrispyFish,
            context = context
        ) } returns resultsReport
        val render = "<html>"
        every { kotlinxHtmlOverallResultsReportRenderer.render(event, resultsReport) } returns render
        val actualDestination = output.resolve("pax.html")
        every { fileOutputResolver.forEventResults(
            event = event,
            type = StandardResultsTypes.pax,
            defaultExtension = "html",
            path = output
        ) } returns actualDestination

        command.parse(arrayOf(
            "${event.id}",
            "--report", "pax",
            "--html",
            "--file", "--file-output", "$output"
        ))

        assertAll {
            assertThat(testConsole.output, "console output").isEmpty()
            assertThat(actualDestination.readText(), "file content").isEqualTo(render)
        }
        verifySequence {
            eventService.findById(event.id)
            crispyFishOverallPaxTimeResultsReportCreator.createFromRegistrationData(
                eventCrispyFishMetadata = eventCrispyFish,
                context = context
            )
            kotlinxHtmlOverallResultsReportRenderer.render(event, resultsReport)
            fileOutputResolver.forEventResults(
                event = event,
                type = StandardResultsTypes.pax,
                defaultExtension = "html",
                path = output
            )
        }
    }
}