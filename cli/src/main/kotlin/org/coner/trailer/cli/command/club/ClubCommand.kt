package org.coner.trailer.cli.command.club

import org.coner.trailer.cli.command.BaseCommand
import org.coner.trailer.cli.command.GlobalModel

class ClubCommand(
    global: GlobalModel
) : BaseCommand(
    global = global,
    help = "Manage club"
) {

    override fun run() = Unit
}