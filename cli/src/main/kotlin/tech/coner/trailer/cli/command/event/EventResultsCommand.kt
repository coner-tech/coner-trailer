package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.defaultByName
import com.github.ajalt.clikt.parameters.groups.groupSwitch
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.*
import tech.coner.trailer.EventContext
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.di.EventResultsSession
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.eventresults.*
import tech.coner.trailer.presentation.model.eventresults.EventResultsModel
import tech.coner.trailer.presentation.text.view.TextView
import tech.coner.trailer.presentation.text.view.eventresults.*
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

    internal inner class CalculatorsContainer(
        override val di: DI
    ) : DIAware {
        override val diContext = diContext { EventResultsSession() }

        val raw: (EventContext) -> RawEventResultsCalculator by factory()
        val pax: (EventContext) -> PaxEventResultsCalculator by factory()
        val clazz: (EventContext) -> ClazzEventResultsCalculator by factory()
        val topTimes: (EventContext) -> TopTimesEventResultsCalculator by factory()
        val comprehensive: (EventContext) -> ComprehensiveEventResultsCalculator by factory()
        val individual: (EventContext) -> IndividualEventResultsCalculator by factory()
    }
    internal val calculators = CalculatorsContainer(di)
    
    internal inner class ViewsContainer(override val di: DI) : DIAware {
        val clazz: MordantClassEventResultsView by instance()
        val comprehensive: TextComprehensiveEventResultsView by instance()
        val individual: TextIndividualEventResultsView by instance()
        val overall: TextOverallEventResultsView by instance()
        val topTimes: TextTopTimesEventResultsView by instance()
    }
    internal val views = ViewsContainer(di)

    internal inner class AdaptersContainer(override val di: DI) : DIAware {
        val clazz: ClassEventResultsModelAdapter by instance()
        val comprehensive: ComprehensiveEventResultsModelAdapter by instance()
        val individual: IndividualEventResultsModelAdapter by instance()
        val overall: OverallEventResultsModelAdapter by instance()
        val topTimes: TopTimesEventResultsModelAdapter by instance()
    }
    internal val adapters = AdaptersContainer(di)

    private val fileOutputResolver: FileOutputDestinationResolver by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val type: EventResultsType by option()
        .choice(StandardEventResultsTypes.all.associateBy { it.key.lowercase() })
        .required()
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

    override suspend fun CoroutineScope.coRun() {
        val eventContext = dataSessionContainer.diContext.use {
            val event = dataSessionContainer.eventService.findByKey(id).getOrThrow()
            dataSessionContainer.eventContextService.load(event).getOrThrow()
        }
        calculators.diContext.use {
            val render = when (type) {
                StandardEventResultsTypes.raw -> handle(calculators.raw(eventContext), adapters.overall, views.overall)
                StandardEventResultsTypes.pax -> handle(calculators.pax(eventContext), adapters.overall, views.overall)
                StandardEventResultsTypes.clazz -> handle(calculators.clazz(eventContext), adapters.clazz, views.clazz)
                StandardEventResultsTypes.topTimes -> handle(calculators.topTimes(eventContext), adapters.topTimes, views.topTimes)
                StandardEventResultsTypes.comprehensive -> handle(calculators.comprehensive(eventContext), adapters.comprehensive, views.comprehensive)
                StandardEventResultsTypes.individual -> handle(calculators.individual(eventContext), adapters.individual, views.individual)
                else -> throw UnsupportedOperationException()
            }
            when (val output = medium) {
                Output.Console -> echo(render)
                is Output.File -> {
                    val actualDestination = fileOutputResolver.forEventResults(
                        event = eventContext.event,
                        type = type,
                        defaultExtension = "txt",
                        path = output.output
                    )
                    actualDestination.writeText(render)
                }
            }
        }
    }

    private fun <ER : EventResults, ERC : EventResultsCalculator<ER>, ERM : EventResultsModel<ER>> handle(
        calculator: ERC,
        adapter: Adapter<ER, ERM>,
        view: TextView<ERM>
    ): String {
        return view(adapter(calculator.calculate()))
    }
}
