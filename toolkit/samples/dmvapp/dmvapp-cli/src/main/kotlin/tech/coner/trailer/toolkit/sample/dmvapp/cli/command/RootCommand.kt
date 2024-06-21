package tech.coner.trailer.toolkit.sample.dmvapp.cli.command

import com.github.ajalt.clikt.core.CliktCommand

class RootCommand : CliktCommand(
    name = "dmvapp-cli"
) {
    override fun run() = Unit
}