package tech.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<TopTimesEventResults>.topTimes() = prop(TopTimesEventResults::topTimes)