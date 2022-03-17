package tech.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.io.service.SeasonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class SeasonGetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
        name = "get",
        help = "Get a Season"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: SeasonService by instance()
    private val view: SeasonView by instance()

    sealed class GetSeasonParam {
        data class ById(val id: UUID) : GetSeasonParam()
        data class ByName(val name: String) : GetSeasonParam()
    }
    private val getSeasonParam: GetSeasonParam by mutuallyExclusiveOptions(
            option("--id").convert { GetSeasonParam.ById(toUuid(it)) },
            option("--name").convert { GetSeasonParam.ByName(it) }
    ).required()

    override fun run() = diContext.use {
        val get = when (val getSeason = getSeasonParam) {
            is GetSeasonParam.ById -> service.findById(getSeason.id)
            is GetSeasonParam.ByName -> service.findByName(getSeason.name)
        }
        echo(view.render(get))
    }
}