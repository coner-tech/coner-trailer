package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.*
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.cli.di.CrispyFishOverallResultsReportCreatorFactory
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.OverallResultsReportTableView
import org.coner.trailer.eventresults.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
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
    private val crispyFishOverallResultsReportCreatorFactory: CrispyFishOverallResultsReportCreatorFactory by factory()
    private val reportTableView: OverallResultsReportTableView by instance()
    private val reportHtmlRenderer: KotlinxHtmlOverallResultsReportRenderer by instance()

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
    enum class Format {
        TEXT,
        HTML
    }
    private val format: Format by option()
        .choice(
            "text" to Format.TEXT,
            "html" to Format.HTML
        )
        .default(Format.TEXT)
    sealed class Output {
        object Console : Output()
        class File(val destination: Path) : Output()
    }
    private val output: Output by mutuallyExclusiveOptions(
        option("--console", help = "Output to console")
            .flag()
            .convert { Output.Console },
        option("--file", help = "Output to file")
            .path(canBeFile = true, canBeDir = false, mustBeWritable = true)
            .convert { Output.File(it) }
    ).default(Output.Console)

    override fun run() {
        val event = eventService.findById(id)
        val reportChoice = report
        val resultsReport = when {
            reportChoice.crispyFish -> {
                val eventCrispyFish = requireNotNull(event.crispyFish) { "Missing crispy fish metadata" }
                crispyFishOverallResultsReportCreatorFactory(reportChoice.resultsType)
                    .createFromRegistrationData(
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
            is Output.File -> output.destination.writeText(render)
        }

    }
}