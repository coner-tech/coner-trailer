package org.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import org.kodein.di.DI
import org.kodein.di.DIAware

class MotorsportRegMemberCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "member"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    override fun run() = Unit
}