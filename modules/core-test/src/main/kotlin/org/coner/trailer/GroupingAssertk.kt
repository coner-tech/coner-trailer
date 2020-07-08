package org.coner.trailer

import assertk.Assert
import assertk.all
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.prop

fun Assert<Grouping>.name() = prop("name") { it.name }
fun Assert<Grouping>.hasName(expected: String) = name().isEqualTo(expected)

fun Assert<Grouping>.abbreviation() = prop("abbreviation") { it.abbreviation }
fun Assert<Grouping>.hasAbbreviation(expected: String) = abbreviation().isEqualTo(expected)

fun Assert<Grouping>.isSingular() = isInstanceOf(Grouping.Singular::class)

fun Assert<Grouping>.isPaired() = isInstanceOf(Grouping.Paired::class)
fun Assert<Grouping.Paired>.pair() = prop("pair") { it.pair }
fun Assert<Grouping.Paired>.first() = pair().prop("first") { it.first }
fun Assert<Grouping.Paired>.second() = pair().prop("second") { it.second}