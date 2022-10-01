package tech.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<IndividualEventResults>.resultsByParticipant() = prop("resultsByParticipant") { it.resultsByIndividual }
