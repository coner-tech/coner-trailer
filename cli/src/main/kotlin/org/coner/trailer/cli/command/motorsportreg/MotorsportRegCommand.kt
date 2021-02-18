package org.coner.trailer.cli.command.motorsportreg

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.cli.di.motorsportRegApiModule
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MotorsportRegCommand(
        useConsole: CliktConsole,
        di: DI
) : CliktCommand(
        name = "motorsportreg",
        help = "Integration with MotorsportReg"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val dbConfig: DatabaseConfiguration by instance()

    override fun run() {
        val msrConfig = dbConfig.motorsportReg
        val username = this.username
                ?: msrConfig?.username
                ?: prompt("Username")
                ?: throw Abort(true)
        val password = this.password
                ?: prompt("Password", hideInput = true)
                ?: throw Abort(true)
        val organizationId = this.organizationId
                ?: msrConfig?.organizationId
                ?: prompt("Organization ID")
                ?: throw Abort(true)
        currentContext.obj = DI {
            extend(this@MotorsportRegCommand.di, copy = Copy.All)
            import(motorsportRegApiModule(
                    username = username,
                    password = password,
                    organizationId = organizationId
            ))
        }
    }
}