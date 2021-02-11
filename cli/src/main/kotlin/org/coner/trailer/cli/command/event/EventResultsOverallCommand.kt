package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.defaultByName
import com.github.ajalt.clikt.parameters.groups.groupSwitch
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.OverallResultsReportTableView
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallHandicapTimeResultsReportCreator
import org.coner.trailer.datasource.crispyfish.eventsresults.OverallRawTimeResultsReportCreator
import org.coner.trailer.render.OverallResultsReportRenderer
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.writeText

@ExperimentalPathApi
class EventResultsOverallCommand(
    di: DI
) : CliktCommand(
    name = "overall",
    help = "Overall Results"
), DIAware {

    override val di by findOrSetObject { di }

    private val eventService: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val crispyFishOverallRawTimeResultsReportCreator: OverallRawTimeResultsReportCreator by instance()
    private val crispyFishOverallHandicapTimeResultsReportCreator: OverallHandicapTimeResultsReportCreator by instance()
    private val reportTableView: OverallResultsReportTableView by instance()
    private val reportHtmlRenderer: OverallResultsReportRenderer by instance()
    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    enum class Report(
        val resultsType: ResultsType,
        val crispyFish: Boolean = false
    ) {
        CrispyFishRaw(resultsType = StandardResultsTypes.overallRawTime, crispyFish = true),
        CrispyFishHandicap(resultsType = StandardResultsTypes.overallHandicapTime, crispyFish = true)
    }
    private val report: Report by option()
        .choice(
            "crispy-fish-raw" to Report.CrispyFishRaw,
            "crispy-fish-handicap" to Report.CrispyFishHandicap
        )
        .required()
    enum class Format(val extension: String) {
        TEXT("txt"),
        HTML("html")
    }
    private val format: Format by option()
        .choice(
            "text" to Format.TEXT,
            "html" to Format.HTML
        )
        .default(Format.TEXT)
    sealed class Output(name: String) : OptionGroup(name) {
        object Console : Output("Console Output")
        class File : Output("File Output") {
            val destination: Path? by option()
                .path(canBeFile = true, canBeDir = true)
        }
    }
    private val output: Output by option()
        .groupSwitch(
            "--console" to Output.Console,
            "--file" to Output.File()
        )
        .defaultByName("--console")

    override fun run() {
        val event = eventService.findById(id)
        val reportChoice = report
        val resultsReport = when {
            reportChoice.crispyFish -> {
                val eventCrispyFish = requireNotNull(event.crispyFish) { "Missing crispy fish metadata" }
                val reportCreator = when (reportChoice.resultsType) {
                    StandardResultsTypes.overallRawTime -> crispyFishOverallRawTimeResultsReportCreator
                    StandardResultsTypes.overallHandicapTime -> crispyFishOverallHandicapTimeResultsReportCreator
                    else -> throw IllegalArgumentException()
                }
                reportCreator.createFromRegistrationData(
                    eventCrispyFishMetadata = eventCrispyFish,
                    context = crispyFishEventMappingContextService.load(eventCrispyFish)
                )
            }
            else -> throw UnsupportedOperationException()
        }
        val render = when (format) {
            Format.TEXT -> reportTableView.render(resultsReport)
            Format.HTML -> reportHtmlRenderer.renderContentOnly(resultsReport)
        }
        when (val output = output) {
            Output.Console -> echo(render)
            is Output.File -> {
                val actualDestination = fileOutputResolver.forEventResults(
                    event = event,
                    type = reportChoice.resultsType,
                    defaultExtension = format.extension,
                    path = output.destination
                )
                actualDestination.writeText(render)
            }
        }

    }
}