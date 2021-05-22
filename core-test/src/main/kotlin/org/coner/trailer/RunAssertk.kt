package org.coner.trailer

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show

fun Assert<Run>.sequence() = prop("sequence") { it.sequence }
fun Assert<Run>.hasSequence(expected: Int) = sequence().isEqualTo(expected)

fun Assert<Run>.time() = prop("time") { it.time }
fun Assert<Run>.hasTime(expected: String?) = time().isEqualTo(expected)

fun Assert<Run>.cones() = prop("cones") { it.cones }
fun Assert<Run>.conesIsNullOrZero() = cones().given { actual ->
    if (actual == null || actual == 0) return@given
    else expected("to be null or 0 but was ${show(actual)}")
}
fun Assert<Run>.hasCones(expected: Int) = cones().isEqualTo(expected)

fun Assert<Run>.didNotFinish() = prop("didNotFinish") { it.didNotFinish }
fun Assert<Run>.isDidNotFinish() = didNotFinish().given { actual ->
    if (actual == true) return@given
    else expected("to be true but was ${show(actual)}")
}

fun Assert<Run>.disqualified() = prop("disqualified") { it.disqualified }

fun Assert<Run>.rerun() = prop("rerun") { it.rerun }
