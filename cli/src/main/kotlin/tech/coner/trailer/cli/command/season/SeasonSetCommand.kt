package tech.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.io.service.SeasonService
import java.util.*

class SeasonSetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "set",
        help = "Set a Season"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: SeasonService by instance()
    private val seasonPointsCalculatorConfigurationService: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option()
    private val seasonPointsCalculatorConfigurationId: UUID? by option().convert { toUuid(it) }
    private val takeScoreCountForPoints: Int? by option().int()

    override fun run() = diContext.use {
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