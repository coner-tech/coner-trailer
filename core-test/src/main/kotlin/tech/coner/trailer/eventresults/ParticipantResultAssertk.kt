package tech.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import tech.coner.trailer.Participant

fun Assert<ParticipantResult>.position() = prop("position") { it.position }
fun Assert<ParticipantResult>.hasPosition(expected: Int) = position().isEqualTo(expected)

fun Assert<ParticipantResult>.score() = prop("score") { it.score }

fun Assert<ParticipantResult>.participant() = prop("participant") { it.participant }
fun Assert<ParticipantResult>.hasParticipant(expected: Participant) = participant().isDataClassEqualTo(expected)

fun Assert<ParticipantResult>.diffFirst() = prop("diffFirst") { it.diffFirst }

fun Assert<ParticipantResult>.diffPrevious() = prop("diffPrevious") { it.diffPrevious }

fun Assert<ParticipantResult>.allRuns() = prop("allRuns") { it.allRuns }

fun Assert<ParticipantResult>.scoredRuns() = prop("scoredRuns") { it.scoredRuns }
fun Assert<ParticipantResult>.hasScoredRuns(expected: List<ResultRun>) = scoredRuns().isEqualTo(expected)

fun Assert<ParticipantResult>.personalBestScoredRunIndex() = prop("personalBestScoredRunIndex") { it.personalBestScoredRunIndex }
fun Assert<ParticipantResult>.personalBestRun() = prop("personalBestRun") { it.personalBestRun }
