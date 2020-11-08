package org.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.SeasonView
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.io.service.SeasonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class SeasonSetCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "set",
        help = "Set a Season"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: SeasonService by instance()
    private val seasonPointsCalculatorConfigurationService: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option()
    private val seasonPointsCalculatorConfigurationId: UUID? by option().convert { toUuid(it) }
    private val takeScoreCountForPoints: Int? by option().int()

    override fun run() {
        val season = service.findById(id)
        val set = season.copy(
                name = name ?: season.name,
                seasonEvents = season.seasonEvents, // https://github.com/caeos/coner-trailer/issues/27
                seasonPointsCalculatorConfiguration = seasonPointsCalculatorConfigurationId?.let {
                    seasonPointsCalculatorConfigurationService.findById(it)
                } ?: season.seasonPointsCalculatorConfiguration,
                takeScoreCountForPoints = takeScoreCountForPoints ?: season.takeScoreCountForPoints
        )
        service.update(set)
        echo(view.render(set))
    }
}