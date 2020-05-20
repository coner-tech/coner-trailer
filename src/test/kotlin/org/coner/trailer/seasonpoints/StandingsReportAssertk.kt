package org.coner.trailer.seasonpoints

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<StandingsReport.Section>.title() = prop("title") { it.title }
fun Assert<StandingsReport.Section>.hasTitle(expected: String) = title().isEqualTo(expected)