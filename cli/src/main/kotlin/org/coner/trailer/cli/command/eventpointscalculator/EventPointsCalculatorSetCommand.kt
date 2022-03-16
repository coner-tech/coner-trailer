package org.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.seasonpoints.EventPointsCalculator
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class EventPointsCalculatorSetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set",
        help = "Set an event points calculator"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
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

    override fun run() = diContext.use {
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