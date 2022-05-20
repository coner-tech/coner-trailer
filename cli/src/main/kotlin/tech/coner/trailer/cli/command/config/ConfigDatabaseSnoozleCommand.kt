package tech.coner.trailer.cli.command.config

import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class ConfigDatabaseSnoozleCommand(
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "snoozle",
    help = """
        Subcommands to manage the Snoozle database of Coner Trailer.
    """.trimIndent()
) {
    override fun run() = Unit
}