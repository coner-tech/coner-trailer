package tech.coner.trailer

import assertk.Assert
import assertk.assertions.prop

fun Assert<Classing>.group() = prop("group") { it.group }
fun Assert<Classing>.handicap() = prop("handicap") { it.handicap }
fun Assert<Classing>.abbreviation() = prop("abbreviation") { it.abbreviation }
fun Assert<Classing>.paxFactor() = prop("paxFactor") { it.paxFactor }