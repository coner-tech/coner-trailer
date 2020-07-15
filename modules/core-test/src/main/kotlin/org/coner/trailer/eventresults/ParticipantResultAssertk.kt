package org.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.coner.trailer.Participant

fun Assert<ParticipantResult>.position() = prop("position") { it.position }
fun Assert<ParticipantResult>.hasPosition(expected: Int) = position().isEqualTo(expected)

fun Assert<ParticipantResult>.score() = prop("score") { it.score }

fun Assert<ParticipantResult>.participant() = prop("participant") { it.participant }
fun Assert<ParticipantResult>.hasParticipant(expected: Participant) = participant().isDataClassEqualTo(expected)

fun Assert<ParticipantResult>.marginOfVictory() = prop("marginOfVictory") { it.marginOfVictory }

fun Assert<ParticipantResult>.marginOfLoss() = prop("marginOfLoss") { it.marginOfLoss }

fun Assert<ParticipantResult>.scoredRuns() = prop("scoredRuns") { it.scoredRuns }
fun Assert<ParticipantResult>.hasScoredRuns(expected: List<ResultRun>) = scoredRuns().isEqualTo(expected)

fun Assert<ParticipantResult>.personalBestRun() = prop("personalBestRun") { it.personalBestRun }
