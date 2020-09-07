package org.coner.trailer.cli.command.seasonpointscalculator

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.*
import org.coner.trailer.cli.util.clikt.toUuid
import org.kodein.di.DI
import org.kodein.di.DIAware
import java.util.*

class SeasonPointsCalculatorAddCommand(
        di: DI,
        useConsole: CliktConsole
) : CliktCommand(
        name = "add",
        help = "Add a season points calculator"
), DIAware {

    init {
        context {
            console = useConsole
        }
    }

    override val di: DI by findOrSetObject { di }

    private val id: UUID? by option()
            .convert { toUuid(it) }
            .default(UUID.randomUUID())
    private val name: String by option()
            .required()
    private val typeCalculatorNamed: List<Pair<String, String>> by option()
            .pair()
            .multiple()

    override fun run() {
        TODO("Not yet implemented")
    }
}