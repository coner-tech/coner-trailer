package tech.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<OverallEventResults>.participantResults() = prop("participantResults") { it.participantResults }