package tech.coner.trailer.app.admin.command.eventpointscalculator

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.app.admin.view.EventPointsCalculatorView
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.EventPointsCalculator
import java.util.*

class EventPointsCalculatorSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set an event points calculator"
) {

    override val diContext = diContextDataSession()
    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    private val id: UUID by argument()
            .convert { toUuid(it) }

    private val name: String? by option()

    private val positionToPoints: List<Pair<Int, Int>> by option()
            .int()
            .pair()
            .multiple()

    sealed class DidNotFinishPoints {
        data class Set(val value: Int) : DidNotFinishPoints()
        object Unset : DidNotFinishPoints()
    }
    private val didNotFinishPoints: DidNotFinishPoints? by mutuallyExclusiveOptions<DidNotFinishPoints>(
            option("--did-not-finish-points")
                    .int()
                    .convert { DidNotFinishPoints.Set(it) },
            option()
                    .switch("--unset-did-not-finish-points" to DidNotFinishPoints.Unset)
    )

    sealed class DidNotStartPoints {
        data class Set(val value: Int) : DidNotStartPoints()
        object Unset : DidNotStartPoints()
    }
    private val didNotStartPoints: DidNotStartPoints? by mutuallyExclusiveOptions<DidNotStartPoints>(
            option("--did-not-start-points")
                    .int()
                    .convert { DidNotStartPoints.Set(it) },
            option()
                    .switch("--unset-did-not-start-points" to DidNotStartPoints.Unset)
    )
    private val defaultPoints: Int? by option()
            .int()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val old = service.findById(id)
        val update = EventPointsCalculator(
                id = old.id,
                name = name ?: old.name,
                positionToPoints = positionToPoints.let { when {
                    it.isNotEmpty() -> it.toMap()
                    else -> old.positionToPoints
                } },
                didNotFinishPoints = when (val didNotFinishPoints = didNotFinishPoints) {
                    is DidNotFinishPoints.Set -> didNotFinishPoints.value
                    DidNotFinishPoints.Unset -> null
                    else -> old.didNotFinishPoints
                },
                didNotStartPoints = when (val didNotStartPoints = didNotStartPoints) {
                    is DidNotStartPoints.Set -> didNotStartPoints.value
                    DidNotStartPoints.Unset -> null
                    else -> old.didNotStartPoints
                },
                defaultPoints = defaultPoints ?: old.defaultPoints
        )
        service.update(update)
        echo(view.render(update))
    }
}