package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class SeasonPointsCalculatorDeleteCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "delete",
        help = "Delete a season points calculator"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: SeasonPointsCalculatorConfigurationService by instance()

    private val id: UUID by argument()
            .convert { toUuid(it) }

    override fun run() = diContext.use {
        val delete = service.findById(id)
        service.delete(delete)
    }
}