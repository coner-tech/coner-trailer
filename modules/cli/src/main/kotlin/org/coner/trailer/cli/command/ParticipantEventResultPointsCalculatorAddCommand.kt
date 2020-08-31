package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import org.coner.snoozle.util.isUuidPattern
import org.coner.trailer.cli.view.ParticipantEventResultPointsCalculatorView
import org.coner.trailer.io.service.ParticipantEventResultPointsCalculatorService
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class ParticipantEventResultPointsCalculatorAddCommand(
        di: DI,
        useConsole: CliktConsole,
        private val view: ParticipantEventResultPointsCalculatorView
) : CliktCommand(
        name = "add",
        help = "Add a participant event result points calculator"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }
    private val service: ParticipantEventResultPointsCalculatorService by instance()

    private val id: UUID by option(hidden = true)
            .convert {
                if (!isUuidPattern.matcher(it).matches())
                    fail("Not a UUID")
                UUID.fromString(it)
            }
            .default(UUID.randomUUID())
    private val name: String by option()
            .required()
            .validate { require(service.hasNewName(it)) { "Name already exists: $it" } }
    private val positionToPoints: List<Pair<Int, Int>> by option()
            .int()
            .pair()
            .multiple(required = true)
    private val didNotFinishPoints: Int? by option().int()
    private val didNotStartPoints: Int? by option().int()
    private val defaultPoints: Int by option().int().required()

    override fun run() {
        val create = ParticipantEventResultPointsCalculator(
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