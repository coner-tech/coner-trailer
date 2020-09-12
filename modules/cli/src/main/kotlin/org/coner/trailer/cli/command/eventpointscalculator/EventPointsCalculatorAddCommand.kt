package org.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventPointsCalculatorView
import org.coner.trailer.io.constraint.EventPointsCalculatorPersistConstraints
import org.coner.trailer.io.service.EventPointsCalculatorService
import org.coner.trailer.seasonpoints.EventPointsCalculator
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class EventPointsCalculatorAddCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "add",
        help = "Add an event points calculator"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
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

    override fun run() {
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