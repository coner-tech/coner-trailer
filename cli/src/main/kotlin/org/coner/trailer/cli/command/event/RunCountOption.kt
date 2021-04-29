package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import org.coner.trailer.Event

fun CliktCommand.runCountOption() = option(
    names = arrayOf("--run-count"),
    help = """
        Count of runs allowed for scoring. 
        Special value "$RUN_COUNT_CRISPY_FISH" will infer run count from crispy fish metadata if present; legacy use only.
    """.trimIndent(),
    metavar = "COUNT"
)
    .convert { doConvertRunCountOptionValue(it) }

private fun OptionCallTransformContext.doConvertRunCountOptionValue(value: String): Event.RunCount {
    return when {
        value == RUN_COUNT_CRISPY_FISH -> Event.RunCount.FromCrispyFish
        value.all { it.isDigit() } -> Event.RunCount.Defined(value.toInt())
        else -> fail(context.localization.intConversionError(value))
    }
}

const val RUN_COUNT_CRISPY_FISH = "from-crispyfish"