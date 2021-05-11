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
import org.coner.trailer.cli.view.GroupedResultsReportTextTableView
import org.coner.trailer.cli.view.OverallResultsReportTextTableView
import org.coner.trailer.datasource.crispyfish.eventsresults.GroupedResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallPaxTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.render.OverallResultsReportHtmlRenderer
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.render.StandaloneReportHtmlRenderer
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
    private val overallReportTextTableView: OverallResultsReportTextTableView by instance()
    private val overallResultsReportHtmlRenderer: OverallResultsReportHtmlRenderer by instance()
    private val groupedResultsReportTextTableView: GroupedResultsReportTextTableView by instance()
    private val standaloneReportHtmlRenderer: StandaloneReportHtmlRenderer by instance()
    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val report: ResultsType by option()
        .choice(
            "raw" to StandardResultsTypes.raw,
            "pax" to StandardResultsTypes.pax,
            "class" to StandardResultsTypes.grouped
        )
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
        return when (format) {
            Format.TEXT -> overallReportTextTableView.render(resultsReport)
            Format.HTML -> standaloneReportHtmlRenderer.renderEventResults(
                event = event,
                resultsReport = resultsReport,
                resultsPartial = overallResultsReportHtmlRenderer.partial(resultsReport)
            )
        }
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
        return when (format) {
            Format.TEXT -> groupedResultsReportTextTableView.render(resultsReport)
            Format.HTML -> TODO()
        }
    }
}