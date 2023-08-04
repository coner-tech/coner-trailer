package tech.coner.trailer.cli.command.person

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.PersonService
import java.util.*

class PersonDeleteCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "delete",
    help = "Delete a person"
) {

    override val diContext = diContextDataSession()
    private val service: PersonService by instance()

    private val id: UUID by argument()
        .convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val delete = service.findById(id)
        service.delete(delete)
    }
}