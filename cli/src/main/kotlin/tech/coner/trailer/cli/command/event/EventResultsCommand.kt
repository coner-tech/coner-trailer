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
import tech.coner.trailer.presentation.di.Format
import tech.coner.trailer.presentation.view.json.JsonView
import tech.coner.trailer.presentation.model.Model
import tech.coner.trailer.presentation.model.eventresults.*
import tech.coner.trailer.presentation.text.view.TextView
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

    override val diContext = diContextDataSession()

    internal inner class ServicesContainer(
        override val di: DI,
        override val diContext: DIContext<*>
    ) : DIAware {
        val events: EventService by instance()
        val eventContexts: EventContextService by instance()
    }
    internal val services = ServicesContainer(di, diContext)

    internal inner class CalculatorFactoriesContainer(
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
    internal val calculatorFactories = CalculatorFactoriesContainer(di)

    internal inner class AdaptersContainer(
        override val di: DI
    ) : DIAware {
        val clazz: Adapter<ClassEventResults, ClassEventResultsModel> by instance()
        val comprehensive: Adapter<ComprehensiveEventResults, ComprehensiveEventResultsModel> by instance()
        val individual: Adapter<IndividualEventResults, IndividualEventResultsModel> by instance()
        val overall: Adapter<OverallEventResults, OverallEventResultsModel> by instance()
        val topTimes: Adapter<TopTimesEventResults, TopTimesEventResultsModel> by instance()
    }
    internal val adapters: AdaptersContainer = AdaptersContainer(di)
    
    internal inner class TextViewsContainer(override val di: DI) : DIAware {
        val clazz: TextView<ClassEventResultsModel> by instance()
        val comprehensive: TextView<ComprehensiveEventResultsModel> by instance()
        val individual: TextView<IndividualEventResultsModel> by instance()
        val overall: TextView<OverallEventResultsModel> by instance()
        val topTimes: TextView<TopTimesEventResultsModel> by instance()
    }
    internal val textViews = TextViewsContainer(di)
    internal val jsonView: JsonView<Model> by instance()

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

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val event = services.events.findByKey(id).getOrThrow()
        val eventContext = services.eventContexts.load(event).getOrThrow()
        calculatorFactories.diContext.use {
            val render = when (type) {
                StandardEventResultsTypes.raw -> handle(calculatorFactories.raw(eventContext), adapters.overall, textViews.overall)
                StandardEventResultsTypes.pax -> handle(calculatorFactories.pax(eventContext), adapters.overall, textViews.overall)
                StandardEventResultsTypes.clazz -> handle(calculatorFactories.clazz(eventContext), adapters.clazz, textViews.clazz)
                StandardEventResultsTypes.topTimes -> handle(calculatorFactories.topTimes(eventContext), adapters.topTimes, textViews.topTimes)
                StandardEventResultsTypes.comprehensive -> handle(calculatorFactories.comprehensive(eventContext), adapters.comprehensive, textViews.comprehensive)
                StandardEventResultsTypes.individual -> handle(calculatorFactories.individual(eventContext), adapters.individual, textViews.individual)
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
        textView: TextView<ERM>,
    ): String {
        val results = calculator.calculate()
        val model = adapter(results)
        return when (global.format) {
            Format.TEXT -> textView(model)
            Format.JSON -> jsonView(model)
        }
    }
}
