package tech.coner.trailer.cli.command.config

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class ConfigDatabaseSnoozleCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "snoozle",
    help = """
        Subcommands to manage the Snoozle database of Coner Trailer.
    """.trimIndent()
) {
    override suspend fun coRun() = Unit
}