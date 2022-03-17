package tech.coner.trailer.cli.command.eventpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.EventPointsCalculatorService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class EventPointsCalculatorDeleteCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "delete",
        help = "Delete an event points calculator"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: EventPointsCalculatorService by instance()

    private val id: UUID by argument()
            .convert { toUuid(it) }

    override fun run() = diContext.use {
        val delete = service.findById(id)
        service.delete(delete)
    }
}