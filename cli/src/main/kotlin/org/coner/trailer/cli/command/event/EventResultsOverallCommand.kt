package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import org.kodein.di.DI
import org.kodein.di.DIAware

class EventResultsOverallCommand(
    di: DI
) : CliktCommand(
    name = "overall",
    help = "Overall Results"
), DIAware {

    override val di by findOrSetObject { di }

    override fun run() {
        TODO("Not yet implemented")
    }
}