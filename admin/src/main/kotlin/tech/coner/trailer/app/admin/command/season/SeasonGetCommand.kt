package tech.coner.trailer.app.admin.command.season

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.app.admin.view.SeasonView
import tech.coner.trailer.io.service.SeasonService
import java.util.*

class SeasonGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get a Season"
) {

    override val diContext = diContextDataSession()
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

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val get = when (val getSeason = getSeasonParam) {
            is GetSeasonParam.ById -> service.findById(getSeason.id)
            is GetSeasonParam.ByName -> service.findByName(getSeason.name)
        }
        echo(view.render(get))
    }
}