package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.prop

fun Assert<Grouping>.name() = prop("name") { it.name }
fun Assert<Grouping>.hasName(expected: String) = name().isEqualTo(expected)

fun Assert<Grouping>.abbreviation() = prop("abbreviation") { it.abbreviation }
fun Assert<Grouping>.hasAbbreviation(expected: String) = abbreviation().isEqualTo(expected)

fun Assert<Grouping>.sort() = prop("sort") { it.sort }
fun Assert<Grouping>.hasSort(expected: Int) = sort().isEqualTo(expected)

fun Assert<Grouping?>.isSingular() = isNotNull().isInstanceOf(Grouping.Singular::class)

fun Assert<Grouping?>.isPaired() = isNotNull().isInstanceOf(Grouping.Paired::class)
fun Assert<Grouping.Paired>.pair() = prop("pair") { it.pair }
fun Assert<Grouping.Paired>.first() = pair().prop("first") { it.first }
fun Assert<Grouping.Paired>.second() = pair().prop("second") { it.second}