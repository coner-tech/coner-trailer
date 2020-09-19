package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import org.coner.trailer.cli.service.StubService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class StubCommand(
        di: DI
) : CliktCommand(
        name = "stub",
        help = "Exploratory command for DI scoped injection"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: StubService by instance()

    override fun run() {
        service.doSomething()
    }
}