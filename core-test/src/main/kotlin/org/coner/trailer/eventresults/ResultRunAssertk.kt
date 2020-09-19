package org.coner.trailer.eventresults

import assertk.Assert
import assertk.all
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.coner.trailer.isEqualTo

fun Assert<ResultRun>.time() = prop("time") { it.time }
fun Assert<ResultRun>.hasTime(expected: String?) = time().isEqualTo(expected)

fun Assert<ResultRun>.cones() = prop("cones") { it.cones }
fun Assert<ResultRun>.conesIsNullOrZero() = cones().given { actual ->
    if (actual == null || actual == 0) return@given
    else expected("to be null or 0 but was ${show(actual)}")
}
fun Assert<ResultRun>.hasCones(expected: Int) = cones().isEqualTo(expected)

fun Assert<ResultRun>.didNotFinish() = prop("didNotFinish") { it.didNotFinish }

fun Assert<ResultRun>.disqualified() = prop("disqualified") { it.disqualified }

fun Assert<ResultRun>.rerun() = prop("rerun") { it.rerun }

fun Assert<ResultRun>.personalBest() = prop("personalBest") { it.personalBest }

fun Assert<ResultRun>.isClean() = all {
    conesIsNullOrZero()
    didNotFinish().isFalse()
    disqualified().isFalse()
    rerun().isFalse()
}