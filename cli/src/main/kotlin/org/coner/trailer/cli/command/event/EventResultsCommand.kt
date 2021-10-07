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
import org.coner.trailer.eventresults.*
import org.coner.trailer.io.service.CrispyFishClassService
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.OverallEventResultsService
import org.coner.trailer.render.*
import org.kodein.di.*
import java.nio.file.Path
import java.util.*
import kotlin.io.path.writeText
import kotlin.reflect.full.isSubclassOf

class EventResultsCommand(
    di: DI
) : CliktCommand(
    name = "results",
    help = "Event Results"
), DIAware {

    override val di by findOrSetObject { di }

    private val eventService: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val overallEventResultsService: OverallEventResultsService by instance()
    private val crispyFishGroupedEventResultsFactory: (Policy) -> GroupedEventResultsFactory by factory()
    private val crispyFishIndividualEventResultsFactory: IndividualEventResultsFactory by instance()
    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val type: EventResultsType by option()
        .choice(StandardEventResultsTypes.all.associateBy { it.key })
        .required()
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
        val render = when (type.clazz) {
            OverallEventResults::class -> renderOverallType(event, buildOverallType(event))
            GroupEventResults::class -> renderGroupedType(event, buildGroupedType(event))
            IndividualEventResults::class -> renderIndividualType(event, buildIndividualType(event))
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

    private fun buildOverallType(event: Event, useType: EventResultsType = type): OverallEventResults {
        return when (useType) {
            StandardEventResultsTypes.raw -> overallEventResultsService.generateRawResults(event)
            StandardEventResultsTypes.pax -> overallEventResultsService.generatePaxResults(event)
            else -> throw IllegalArgumentException()
        }
    }

    private fun renderOverallType(event: Event, results: OverallEventResults): String {
        val factory = di.direct.factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format)
        val renderer = factory(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun buildGroupedType(event: Event): GroupEventResults {
        return when (event.policy.authoritativeRunSource) {
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
    }

    private fun renderGroupedType(event: Event, results: GroupEventResults): String {
        val factory = di.direct.factory<List<EventResultsColumn>, GroupEventResultsRenderer<String, *>>(format)
        val renderer = factory(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun buildIndividualType(event: Event): IndividualEventResults {
        return crispyFishIndividualEventResultsFactory.factory(
            overallEventResults = StandardEventResultsTypes.allForIndividual
                .filter { it.clazz.isSubclassOf(OverallEventResults::class) }
                .map { buildOverallType(event = event, useType = it) },
            groupEventResults =  StandardEventResultsTypes.allForIndividual
                .filter { it.clazz.isSubclassOf(GroupEventResults::class) }
                .map { buildGroupedType(event = event) }
        )
    }

    private fun renderIndividualType(event: Event, results: IndividualEventResults): String {
        val renderer = di.direct.instance<IndividualEventResultsRenderer<String, *>>(format)
        return renderer.render(event, results)
    }
}