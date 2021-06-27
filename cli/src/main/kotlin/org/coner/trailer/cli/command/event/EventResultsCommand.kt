package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.*
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.switch
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.datasource.crispyfish.eventsresults.GroupedResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallPaxTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.asciitable.AsciiTableGroupedResultsReportRenderer
import org.coner.trailer.render.asciitable.AsciiTableOverallResultsReportRenderer
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlGroupedResultsReportRenderer
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.render.standardEventResultsReportColumns
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
import org.kodein.di.instance
import java.nio.file.Path
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.writeText

@ExperimentalPathApi
class EventResultsCommand(
    di: DI
) : CliktCommand(
    name = "results",
    help = "Overall Results"
), DIAware {

    override val di by findOrSetObject { di }

    private val eventService: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val crispyFishRawResultsReportCreator: (Policy) -> OverallRawTimeResultsReportCreator by factory()
    private val crispyFishPaxResultsReportCreator: (Policy) -> OverallPaxTimeResultsReportCreator by factory()
    private val crispyFishGroupedResultsReportCreator: (Policy) -> GroupedResultsReportCreator by factory()
    private val asciiTableOverallResultsReportRenderer: (List<EventResultsReportColumn>) -> AsciiTableOverallResultsReportRenderer by factory()
    private val kotlinxHtmlOverallResultsReportRenderer: (List<EventResultsReportColumn>) -> KotlinxHtmlOverallResultsReportRenderer by factory()
    private val asciiTableGroupedResultsReportRenderer: (List<EventResultsReportColumn>) -> AsciiTableGroupedResultsReportRenderer by factory()
    private val kotlinxHtmlGroupedResultsReportRenderer: (List<EventResultsReportColumn>) -> KotlinxHtmlGroupedResultsReportRenderer by factory()
    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val report: ResultsType by option()
        .choice(StandardResultsTypes.all.associateBy { it.key })
        .required()
    enum class Format(val extension: String) {
        TEXT("txt"),
        HTML("html")
    }
    private val format: Format by option(help = "Select output format")
        .switch(
            "--text" to Format.TEXT,
            "--html" to Format.HTML
        )
        .default(Format.TEXT)
    sealed class Output(help: String) : OptionGroup(help = help) {
        object Console : Output(help = "Output to console")
        class File : Output(help = "Output to file") {
            val output: Path? by option(
                help = "Select file output target",
                names = arrayOf("-fo", "--file-output"),
            )
                .path(canBeFile = true, canBeDir = true)
        }
    }
    private val medium: Output? by option(help = "Select output medium")
        .groupSwitch(
            "--console" to Output.Console,
            "--file" to Output.File()
        )
        .defaultByName("--console")

    override fun run() {
        val event = eventService.findById(id)
        val render = when (report) {
            StandardResultsTypes.raw, StandardResultsTypes.pax -> buildOverallTypeReport(event)
            StandardResultsTypes.grouped -> buildGroupedTypeReport(event)
            else -> throw UnsupportedOperationException()
        }
        when (val output = medium) {
            Output.Console -> echo(render)
            is Output.File -> {
                val actualDestination = fileOutputResolver.forEventResults(
                    event = event,
                    type = report,
                    defaultExtension = format.extension,
                    path = output.output
                )
                actualDestination.writeText(render)
            }
        }
    }

    private fun buildOverallTypeReport(event: Event): String {
        val resultsReport = when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> {
                val eventCrispyFish = event.requireCrispyFish()
                val reportCreator = when (report) {
                    StandardResultsTypes.raw -> crispyFishRawResultsReportCreator(event.policy)
                    StandardResultsTypes.pax -> crispyFishPaxResultsReportCreator(event.policy)
                    else -> throw IllegalArgumentException()
                }
                reportCreator.createFromRegistrationData(
                    eventCrispyFishMetadata = eventCrispyFish,
                    context = crispyFishEventMappingContextService.load(eventCrispyFish)
                )
            }
        }
        val rendererFactory = when (format) {
            Format.TEXT -> asciiTableOverallResultsReportRenderer
            Format.HTML -> kotlinxHtmlOverallResultsReportRenderer
        }
        return rendererFactory(standardEventResultsReportColumns)
            .render(event, resultsReport)
    }

    private fun buildGroupedTypeReport(event: Event): String {
        val resultsReport = when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> {
                val eventCrispyFish = event.requireCrispyFish()
                val reportCreator = crispyFishGroupedResultsReportCreator(event.policy)
                reportCreator.createFromRegistrationData(
                    eventCrispyFishMetadata = eventCrispyFish,
                    context = crispyFishEventMappingContextService.load(eventCrispyFish)
                )
            }
        }
        val rendererFactory = when (format) {
            Format.TEXT -> asciiTableGroupedResultsReportRenderer
            Format.HTML -> kotlinxHtmlGroupedResultsReportRenderer
        }
        return rendererFactory(standardEventResultsReportColumns)
            .render(event, resultsReport)
    }
}