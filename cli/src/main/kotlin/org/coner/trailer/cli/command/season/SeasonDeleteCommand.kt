package org.coner.trailer.cli.command.season

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.io.service.SeasonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class SeasonDeleteCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "delete",
        help = "Delete a Season"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val service: SeasonService by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override fun run() {
        val delete = service.findById(id)
        service.delete(delete)
    }
}