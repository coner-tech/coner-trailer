package tech.coner.trailer.cli.command.season

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Season
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.io.service.SeasonService
import java.util.*

class SeasonAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "add",
    help = "Add a Season"
) {

    override val diContext = diContextDataSession()
    private val service: SeasonService by instance()
    private val spccService: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonView by instance()

    private val id: UUID? by option(hidden = true).convert { toUuid(it) }
    private val name: String by option().required()

    sealed class SeasonPointsCalculatorConfigurationParam {
        data class ById(val id: UUID) : SeasonPointsCalculatorConfigurationParam()
        data class ByName(val name: String) : SeasonPointsCalculatorConfigurationParam()
    }
    private val seasonPointsCalculatorConfigurationParam: SeasonPointsCalculatorConfigurationParam by mutuallyExclusiveOptions(
        option("--season-points-calculator-id")
            .convert { SeasonPointsCalculatorConfigurationParam.ById(toUuid(it)) },
        option("--season-points-calculator-named")
            .convert { SeasonPointsCalculatorConfigurationParam.ByName(it) }
    ).required()
    private val takeScoreCountForPoints: Int? by option().int()

    override suspend fun coRun() = diContext.use {
        val create = Season(
            id = id ?: UUID.randomUUID(),
            name = name,
            seasonEvents = emptyList(), // https://github.com/caeos/coner-trailer/issues/27
            seasonPointsCalculatorConfiguration = when (val spccParam = seasonPointsCalculatorConfigurationParam) {
                is SeasonPointsCalculatorConfigurationParam.ById -> spccService.findById(spccParam.id)
                is SeasonPointsCalculatorConfigurationParam.ByName -> spccService.findByName(spccParam.name)
            },
            takeScoreCountForPoints = takeScoreCountForPoints
        )
        service.create(create)
        echo(view.render(create))
    }
}