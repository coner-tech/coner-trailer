package tech.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.EventPointsCalculatorView
import tech.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import tech.coner.trailer.io.service.EventPointsCalculatorService
import tech.coner.trailer.seasonpoints.EventPointsCalculator
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class EventPointsCalculatorAddCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "add",
        help = "Add an event points calculator"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val constraints: EventPointsCalculatorPersistConstraints by instance()
    private val service: EventPointsCalculatorService by instance()
    private val view: EventPointsCalculatorView by instance()

    private val id: UUID by option(hidden = true)
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val name: String by option()
            .required()
            .validate {
                if (!constraints.hasUniqueName(id, it))
                    fail("Name must be unique")
            }
    private val positionToPoints: List<Pair<Int, Int>> by option()
            .int()
            .pair()
            .multiple(required = true)
    private val didNotFinishPoints: Int? by option().int()
    private val didNotStartPoints: Int? by option().int()
    private val defaultPoints: Int by option().int().required()

    override fun run() = diContext.use {
        val create = EventPointsCalculator(
                id = id,
                name = name,
                positionToPoints = positionToPoints.toMap(),
                didNotFinishPoints = didNotFinishPoints,
                didNotStartPoints = didNotStartPoints,
                defaultPoints = defaultPoints
        )
        service.create(create)
        echo(view.render(create))
    }
}