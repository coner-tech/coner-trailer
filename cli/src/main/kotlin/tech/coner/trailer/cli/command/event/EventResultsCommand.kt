package tech.coner.trailer.cli.command.event

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
import org.kodein.di.*
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.di.EventResultsSession
import tech.coner.trailer.di.Format
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.render.*
import java.nio.file.Path
import java.util.*
import kotlin.io.path.writeText

class EventResultsCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "results",
    help = "Event Results"
) {

    private val dataSession = object : DIAware {
        override val di = this@EventResultsCommand.di
        override val diContext = diContextDataSession()

        val eventService: EventService by instance()
        val eventContextService: EventContextService by instance()
    }
    private val eventResultsSession = object : DIAware {
        override val di = this@EventResultsCommand.di
        override val diContext = diContext { EventResultsSession() }

        val rawFactory: (EventContext) -> RawEventResultsCalculator by factory()
        val paxFactory: (EventContext) -> PaxEventResultsCalculator by factory()
        val clazzFactory: (EventContext) -> ClazzEventResultsCalculator by factory()
        val comprehensiveFactory: (EventContext) -> ComprehensiveEventResultsCalculator by factory()
        val individualFactory: (EventContext) -> IndividualEventResultsCalculator by factory()
    }

    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val type: EventResultsType by option()
        .choice(StandardEventResultsTypes.all.associateBy { it.key })
        .required()
    private val format: Format by option(help = "Select output format")
        .choice(
            "json" to Format.JSON,
            "text" to Format.TEXT,
            "html" to Format.HTML
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

    override suspend fun coRun() {
        val eventContext = dataSession.diContext.use {
            val event = dataSession.eventService.findById(id)
            dataSession.eventContextService.load(event).getOrThrow()
        }
        eventResultsSession.diContext.use {
            val render = when (type) {
                StandardEventResultsTypes.raw -> renderOverallType(
                    event = eventContext.event,
                    results = eventResultsSession.rawFactory(eventContext).calculate()
                )
                StandardEventResultsTypes.pax -> renderOverallType(
                    event = eventContext.event,
                    results = eventResultsSession.paxFactory(eventContext).calculate()
                )
                StandardEventResultsTypes.clazz -> renderGroupType(
                    event = eventContext.event,
                    results = eventResultsSession.clazzFactory(eventContext).calculate()
                )
                StandardEventResultsTypes.comprehensive -> renderComprehensiveType(
                    event = eventContext.event,
                    results = eventResultsSession.comprehensiveFactory(eventContext).calculate()
                )
                StandardEventResultsTypes.individual -> renderIndividualType(
                    event = eventContext.event,
                    results = eventResultsSession.individualFactory(eventContext).calculate()
                )
                else -> throw UnsupportedOperationException()
            }
            when (val output = medium) {
                Output.Console -> echo(render)
                is Output.File -> {
                    val actualDestination = fileOutputResolver.forEventResults(
                        event = eventContext.event,
                        type = type,
                        defaultExtension = format.extension,
                        path = output.output
                    )
                    actualDestination.writeText(render)
                }
            }
        }
    }

    private fun renderOverallType(event: Event, results: OverallEventResults): String {
        val factory = di.direct.factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format)
        val renderer = factory(standardEventResultsColumns)
        return renderer.render(event, results)
    }

    private fun renderGroupType(event: Event, results: ClazzEventResults): String {
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