package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.TestClasses
import org.coner.trailer.TestParticipants
import org.coner.trailer.eventresults.StandardEventResultsTypes.clazz
import org.coner.trailer.eventresults.StandardEventResultsTypes.pax
import org.coner.trailer.eventresults.StandardEventResultsTypes.raw

object TestIndividualEventResults {
    object Lscc2019Simplified {
        private val testClasses = TestClasses.Lscc2019
        val points1: IndividualEventResults by lazy {
            val participants = TestParticipants.Lscc2019Points1Simplified
            val rawResults = TestOverallRawEventResults.Lscc2019Simplified.points1
            val paxResults = TestOverallPaxEventResults.Lscc2019Simplified.points1
            val clazzResults = TestClazzEventResults.Lscc2019Simplified.points1
            IndividualEventResults(
                type = StandardEventResultsTypes.individual,
                runCount = 5,
                allByParticipant = sortedMapOf(
                    comparator = compareBy(Participant::lastName, Participant::firstName, Participant::signageClassingNumber),
                    participants.ANASTASIA_RIGLER to mapOf(
                        raw to rawResults.participantResults[3],
                        pax to paxResults.participantResults[2],
                        clazz to clazzResults.groupParticipantResults[testClasses.HS]!![0]
                    ),
                    participants.REBECCA_JACKSON to mapOf(
                        raw to rawResults.participantResults[4],
                        pax to paxResults.participantResults[3],
                        clazz to clazzResults.groupParticipantResults[testClasses.HS]!![1]
                    ),
                    participants.EUGENE_DRAKE to mapOf(
                        raw to rawResults.participantResults[0],
                        pax to paxResults.participantResults[1],
                        clazz to clazzResults.groupParticipantResults[testClasses.STR]!![0]
                    ),
                    participants.JIMMY_MCKENZIE to mapOf(
                        raw to rawResults.participantResults[2],
                        pax to paxResults.participantResults[4],
                        clazz to clazzResults.groupParticipantResults[testClasses.STR]!![1]
                    ),
                    participants.BRANDY_HUFF to mapOf(
                        raw to rawResults.participantResults[1],
                        pax to paxResults.participantResults[0],
                        clazz to clazzResults.groupParticipantResults[testClasses.NOV]!![0]
                    ),
                    participants.BRYANT_MORAN to mapOf(
                        raw to rawResults.participantResults[5],
                        pax to paxResults.participantResults[5],
                        clazz to clazzResults.groupParticipantResults[testClasses.NOV]!![1]
                    ),
                    participants.DOMINIC_ROGERS to mapOf(
                        raw to rawResults.participantResults[6],
                        pax to paxResults.participantResults[6],
                        clazz to clazzResults.groupParticipantResults[testClasses.NOV]!![2]
                    )
                ),
                innerEventResultsTypes = listOf(
                    rawResults.type,
                    paxResults.type,
                    clazzResults.type
                )
            )
        }
    }
}