package tech.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop

fun Assert<Run>.sequence() = prop("sequence") { it.sequence }
fun Assert<Run>.hasSequence(expected: Int) = sequence().isEqualTo(expected)

fun Assert<Run>.time() = prop("time") { it.time }
fun Assert<Run>.hasTime(expected: String?) = time().isEqualTo(expected)

fun Assert<Run>.cones() = prop("cones") { it.cones }

fun Assert<Run>.didNotFinish() = prop("didNotFinish") { it.didNotFinish }

fun Assert<Run>.disqualified() = prop("disqualified") { it.disqualified }

fun Assert<Run>.rerun() = prop("rerun") { it.rerun }

fun Assert<Run>.clean() = prop(Run::clean)
fun Assert<Run>.effectivePenalty() = prop(Run::effectivePenalty)
fun Assert<Run>.supersededPenalties() = prop(Run::supersededPenalties)
fun Assert<Run>.allPenalties() = prop(Run::allPenalties)