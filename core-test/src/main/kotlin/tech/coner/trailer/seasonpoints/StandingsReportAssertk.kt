package tech.coner.trailer.seasonpoints

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.isSameInstanceAs
import assertk.assertions.prop
import tech.coner.trailer.Person

fun Assert<StandingsReport.Section>.title() = prop("title") { it.title }
fun Assert<StandingsReport.Section>.hasTitle(expected: String) = title().isEqualTo(expected)

fun Assert<StandingsReport.Section>.standings() = prop("standings") { it.standings }

fun Assert<StandingsReport.Standing>.position() = prop("position") { it.position }
fun Assert<StandingsReport.Standing>.hasPosition(expected: Int) = position().isEqualTo(expected)

fun Assert<StandingsReport.Standing>.score() = prop("score") { it.score }
fun Assert<StandingsReport.Standing>.hasScore(expected: Int) = score().isEqualTo(expected)

fun Assert<StandingsReport.Standing>.person() = prop("person") { it.person }
fun Assert<StandingsReport.Standing>.hasPerson(expected: Person) = person().isSameInstanceAs(expected)

fun Assert<StandingsReport.Standing>.tie() = prop("tie") { it.tie }
fun Assert<StandingsReport.Standing>.isTie() = tie().isEqualTo(true)
fun Assert<StandingsReport.Standing>.isNotTie() = tie().isEqualTo(false)