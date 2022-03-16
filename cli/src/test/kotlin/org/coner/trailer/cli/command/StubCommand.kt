package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.service.StubService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class StubCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "stub",
    help = "Exploratory command for DI scoped injection"
), DIAware {

    override val diContext = diContext { global.requireEnvironment() }
    private val service: StubService by instance()

    override fun run() {
        service.doSomething()
    }
}