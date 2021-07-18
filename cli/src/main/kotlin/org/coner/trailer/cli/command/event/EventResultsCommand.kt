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
import com.github.ajalt.clikt.parameters.options.switch
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.datasource.crispyfish.eventresults.GroupedEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.OverallPaxTimeEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.OverallRawEventResultsFactory
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.StandardEventResultsTypes
import org.coner.trailer.io.service.CrispyFishClassService
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.render.EventResultsColumn
import org.coner.trailer.render.html.HtmlGroupedEventResultsRenderer
import org.coner.trailer.render.html.HtmlOverallEventResultsRenderer
import org.coner.trailer.render.json.JsonGroupedEventResultsRenderer
import org.coner.trailer.render.json.JsonOverallEventResultsRenderer
import org.coner.trailer.render.standardEventResultsColumns
import org.coner.trailer.render.text.TextGroupedEventResultsRenderer
import org.coner.trailer.render.text.TextOverallEventResultsRenderer
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
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val crispyFishOverallRawEventResultsFactory: (Policy) -> OverallRawEventResultsFactory by factory()
    private val crispyFishOverallPaxEventResultsFactory: (Policy) -> OverallPaxTimeEventResultsFactory by factory()
    private val crispyFishGroupedEventResultsFactory: (Policy) -> GroupedEventResultsFactory by factory()
    private val jsonOverallEventResultsRenderer: (List<EventResultsColumn>) -> JsonOverallEventResultsRenderer by factory()
    private val jsonGroupedEventResultsRenderer: (List<EventResultsColumn>) -> JsonGroupedEventResultsRenderer by factory()
    private val textOverallEventResultsRenderer: (List<EventResultsColumn>) -> TextOverallEventResultsRenderer by factory()
    private val textGroupedEventResultsRenderer: (List<EventResultsColumn>) -> TextGroupedEventResultsRenderer by factory()
    private val htmlOverallEventResultsRenderer: (List<EventResultsColumn>) -> HtmlOverallEventResultsRenderer by factory()
    private val htmlGroupedEventResultsRenderer: (List<EventResultsColumn>) -> HtmlGroupedEventResultsRenderer by factory()
    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val type: EventResultsType by option()
        .choice(StandardEventResultsTypes.all.associateBy { it.key })
        .required()
    enum class Format(val extension: String) {
        JSON("json"),
        TEXT("txt"),
        HTML("html")
    }
    private val format: Format by option(help = "Select output format")
        .switch(
            "--json" to Format.JSON,
            "--text" to Format.TEXT,
            "--html" to Format.HTML
        )
        .default(Format.JSON)
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
        val render = when (type) {
            StandardEventResultsTypes.raw, StandardEventResultsTypes.pax -> buildOverallType(event)
            StandardEventResultsTypes.grouped -> buildGroupedType(event)
            else -> throw UnsupportedOperationException()
        }
        when (val output = medium) {
            Output.Console -> echo(render)
            is Output.File -> {
                val actualDestination = fileOutputResolver.forEventResults(
                    event = event,
                    type = type,
                    defaultExtension = format.extension,
                    path = output.output
                )
                actualDestination.writeText(render)
            }
        }
    }

    private fun buildOverallType(event: Event): String {
        val results = when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> {
                val eventCrispyFish = event.requireCrispyFish()
                val factory = when (type) {
                    StandardEventResultsTypes.raw -> crispyFishOverallRawEventResultsFactory(event.policy)
                    StandardEventResultsTypes.pax -> crispyFishOverallPaxEventResultsFactory(event.policy)
                    else -> throw IllegalArgumentException()
                }
                factory.factory(
                    eventCrispyFishMetadata = eventCrispyFish,
                    allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(
                        crispyFishClassDefinitionFile = eventCrispyFish.classDefinitionFile
                    ),
                    context = crispyFishEventMappingContextService.load(eventCrispyFish)
                )
            }
        }
        val renderer = when (format) {
            Format.JSON -> jsonOverallEventResultsRenderer
            Format.TEXT -> textOverallEventResultsRenderer
            Format.HTML -> htmlOverallEventResultsRenderer
        }(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun buildGroupedType(event: Event): String {
        val results = when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> {
                val eventCrispyFish = event.requireCrispyFish()
                val factory = crispyFishGroupedEventResultsFactory(event.policy)
                factory.factory(
                    eventCrispyFishMetadata = eventCrispyFish,
                    allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(
                        crispyFishClassDefinitionFile = eventCrispyFish.classDefinitionFile
                    ),
                    context = crispyFishEventMappingContextService.load(eventCrispyFish)
                )
            }
        }
        val renderer = when (format) {
            Format.JSON -> jsonGroupedEventResultsRenderer
            Format.TEXT -> textGroupedEventResultsRenderer
            Format.HTML -> htmlGroupedEventResultsRenderer
        }(standardEventResultsColumns)
        return renderer.render(event, results)
    }
}