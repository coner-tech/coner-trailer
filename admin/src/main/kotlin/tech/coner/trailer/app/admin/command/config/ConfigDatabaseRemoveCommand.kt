package tech.coner.trailer.app.admin.command.config

import com.github.ajalt.clikt.parameters.arguments.argument
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseRemoveCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "remove",
        help = """
                Remove a database from the CLI app's config file.
                
                This is a non-destructive operation -- you can always add the database again. This will not affect the
                database itself.
            """.trimIndent()
) {

    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()

    private val name: String by argument()

    override suspend fun CoroutineScope.coRun() {
        service.removeDatabase(name).getOrThrow()
    }
}