package org.coner.trailer.cli.command.grouping

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.groupChoice
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.github.ajalt.clikt.parameters.options.required

fun CliktCommand.groupingOption() = option()
    .groupChoice(
        "singular" to GroupingOption.Singular(),
        "paired" to GroupingOption.Paired()
    )

sealed class GroupingOption : OptionGroup() {
    class Singular : GroupingOption() {
        val abbreviationSingular: String by option().required()
    }
    class Paired : GroupingOption() {
        val abbreviationsPaired: Pair<String, String> by option().pair().required()
    }
}