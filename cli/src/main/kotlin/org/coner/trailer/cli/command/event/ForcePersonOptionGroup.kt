package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.pair
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.choice
import org.coner.trailer.cli.util.clikt.toUuid
import java.util.*

class ForcePersonOptionGroup : OptionGroup() {
    val groupingType: TypeChoice by option(
        names = arrayOf("--grouping-type"),
        help = "Grouping singular (CS) vs paired (NOV CS)"
    ).choice(
        "singular" to TypeChoice.SINGULAR,
        "paired" to TypeChoice.PAIRED
    ).required()
    val groupingAbbreviationSingular: String? by option(
        names = arrayOf("--grouping-abbreviation-singular"),
        help = "Abbreviation of singular grouping (CS)"
    )
    val groupingAbbreviationPaired: Pair<String, String>? by option(
        names = arrayOf("--grouping-abbreviation-paired"),
        help = "Abbreviation of paired grouping (NOV CS)"
    ).pair()
    val number: String by option(
        names = arrayOf("--number"),
        help = "Number of the participant"
    ).required()
    val personId: UUID by option(
        names = arrayOf("--person-id"),
        help = "Person ID to force onto participant whose registration matches the given signage"
    ).convert { toUuid(it) }.required()

    enum class TypeChoice {
        SINGULAR,
        PAIRED
    }
}