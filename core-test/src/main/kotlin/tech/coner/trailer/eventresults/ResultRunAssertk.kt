package tech.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<ResultRun>.run() = prop("run") { it.run }

fun Assert<ResultRun>.score() = prop("score") { it.score }
