package org.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<IndividualEventResults>.allByParticipant() = prop("allByParticipant") { it.allByParticipant }
