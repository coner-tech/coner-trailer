package tech.coner.trailer.cli.command.event

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
import org.kodein.di.*
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

    internal inner class DataSessionContainer(
        override val di: DI
    ) : DIAware {
        override val diContext = diContextDataSession()

        val eventService: EventService by instance()
        val eventContextService: EventContextService by instance()
    }
    internal val dataSessionContainer = DataSessionContainer(di)

    internal inner class EventResultsSessionContainer(
        override val di: DI
    ) : DIAware {
        override val diContext = diContext { EventResultsSession() }

        val rawCalculator: (EventContext) -> RawEventResultsCalculator by factory()
        val paxCalculator: (EventContext) -> PaxEventResultsCalculator by factory()
        val clazzCalculator: (EventContext) -> ClazzEventResultsCalculator by factory()
        val topTimesCalculator: (EventContext) -> TopTimesEventResultsCalculator by factory()
        val comprehensiveCalculator: (EventContext) -> ComprehensiveEventResultsCalculator by factory()
        val individualCalculator: (EventContext) -> IndividualEventResultsCalculator by factory()
    }
    internal val eventResultsSessionContainer = EventResultsSessionContainer(di)

    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val type: EventResultsType by option()
        .choice(StandardEventResultsTypes.all.associateBy { it.key.lowercase() })
        .required()
    private val format: Format by option(help = "Select output format")
        .choice(
            "json" to Format.JSON,
            "text" to Format.TEXT,
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
    private val medium: Output by option(help = "Select output medium")
        .groupSwitch(
            "--console" to Output.Console,
            "--file" to Output.File()
        )
        .defaultByName("--console")

    override suspend fun coRun() {
        val eventContext = dataSessionContainer.diContext.use {
            val event = dataSessionContainer.eventService.findByKey(id).getOrThrow()
            dataSessionContainer.eventContextService.load(event).getOrThrow()
        }
        eventResultsSessionContainer.diContext.use {
            val render = when (type) {
                StandardEventResultsTypes.raw -> renderOverallType(
                    eventContext = eventContext,
                    results = eventResultsSessionContainer.rawCalculator(eventContext).calculate()
                )
                StandardEventResultsTypes.pax -> renderOverallType(
                    eventContext = eventContext,
                    results = eventResultsSessionContainer.paxCalculator(eventContext).calculate()
                )
                StandardEventResultsTypes.clazz -> renderGroupType(
                    eventContext = eventContext,
                    results = eventResultsSessionContainer.clazzCalculator(eventContext).calculate()
                )
                StandardEventResultsTypes.topTimes -> renderTopTimesType(
                    eventContext = eventContext,
                    results = eventResultsSessionContainer.topTimesCalculator(eventContext).calculate()
                )
                StandardEventResultsTypes.comprehensive -> renderComprehensiveType(
                    eventContext = eventContext,
                    results = eventResultsSessionContainer.comprehensiveCalculator(eventContext).calculate()
                )
                StandardEventResultsTypes.individual -> renderIndividualType(
                    eventContext = eventContext,
                    results = eventResultsSessionContainer.individualCalculator(eventContext).calculate()
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

    private fun renderOverallType(eventContext: EventContext, results: OverallEventResults): String {
        val renderer = di.direct.instance<OverallEventResultsRenderer<String, *>>(format)
        return renderer.render(eventContext, results)
    }

    private fun renderGroupType(eventContext: EventContext, results: ClazzEventResults): String {
        val renderer = di.direct.instance<ClazzEventResultsRenderer<String, *>>(format)
        return renderer.render(eventContext, results)
    }

    private fun renderTopTimesType(eventContext: EventContext, results: TopTimesEventResults): String {
        val renderer: TopTimesEventResultsRenderer<String, *> = di.direct.instance(format)
        return renderer.render(eventContext, results)
    }

    private fun renderComprehensiveType(eventContext: EventContext, results: ComprehensiveEventResults): String {
        val renderer = di.direct.instance<ComprehensiveEventResultsRenderer<String, *>>(format)
        return renderer.render(eventContext, results)
    }

    private fun renderIndividualType(eventContext: EventContext, results: IndividualEventResults): String {
        val renderer = di.direct.instance<IndividualEventResultsRenderer<String, *>>(format)
        return renderer.render(eventContext, results)
    }
}