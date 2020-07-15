package org.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<OverallResultsReport>.participantResults() = prop("participantResults") { it.participantResults }