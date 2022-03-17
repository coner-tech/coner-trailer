package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
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
import tech.coner.trailer.Event
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.render.*
import org.kodein.di.*
import java.nio.file.Path
import java.util.*
import kotlin.io.path.writeText

class EventResultsCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "results",
    help = "Event Results"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val eventService: EventService by instance()
    private val eventResultsService: EventResultsService by instance()
    private val individualEventResultsService: IndividualEventResultsService by instance()
    private val comprehensiveEventResultsService: ComprehensiveEventResultsService by instance()
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

    override fun run() = diContext.use {
        val event = eventService.findById(id)
        val render = when (type.clazz) {
            OverallEventResults::class -> renderOverallType(event, eventResultsService.buildOverallTypeResults(event, type))
            GroupEventResults::class -> renderGroupType(event, eventResultsService.buildGroupTypeResults(event, type))
            ComprehensiveEventResults::class -> renderComprehensiveType(event, comprehensiveEventResultsService.build(event))
            IndividualEventResults::class -> renderIndividualType(event, individualEventResultsService.build(event))
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

    private fun renderOverallType(event: Event, results: OverallEventResults): String {
        val factory = di.direct.factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format)
        val renderer = factory(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun renderGroupType(event: Event, results: GroupEventResults): String {
        val factory = di.direct.factory<List<EventResultsColumn>, GroupEventResultsRenderer<String, *>>(format)
        val renderer = factory(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun renderComprehensiveType(event: Event, results: ComprehensiveEventResults): String {
        val factory = di.direct.factory<List<EventResultsColumn>, ComprehensiveEventResultsRenderer<String, *>>(format)
        val renderer = factory(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun renderIndividualType(event: Event, results: IndividualEventResults): String {
        val renderer = di.direct.instance<IndividualEventResultsRenderer<String, *>>(format)
        return renderer.render(event, results)
    }
}