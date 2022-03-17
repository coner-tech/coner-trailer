package tech.coner.trailer.cli.command.club

import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class ClubCommand(
    global: GlobalModel
) : BaseCommand(
    global = global,
    help = "Manage club"
) {

    override fun run() = Unit
}