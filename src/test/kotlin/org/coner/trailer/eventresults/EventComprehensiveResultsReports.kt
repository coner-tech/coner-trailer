package org.coner.trailer.eventresults

import org.coner.trailer.*

object EventComprehensiveResultsReports {

    val THSCC_2019_POINTS_1: EventComprehensiveResultsReport
        get() = EventComprehensiveResultsReport(
                rawTimeResults = EventOverallResultsReport(
                        participantResults = listOf() 
                ),
                handicapTimeResults = EventOverallResultsReport(
                        participantResults = listOf()
                ),
                classedResults = GroupedResultsReport(
                        groupingsToResultsMap = mapOf(
                                TestGroupings.THSCC_2019_NOV to listOf(
                                        ParticipantResult(
                                                position = 4,
                                                participant = TestParticipants.Thscc2019Points1.BRANDY_HUFF
                                        ),
                                        ParticipantResult(
                                                position = 9,
                                                participant = TestParticipants.Thscc2019Points1.BRYANT_MORAN
                                        ),
                                        ParticipantResult(
                                                position = 11,
                                                participant = TestParticipants.Thscc2019Points1.DOMINIC_ROGERS
                                        )
                                ),
                                TestGroupings.THSCC_2019_STR to listOf(
                                        ParticipantResult(
                                                position = 2,
                                                participant = TestParticipants.Thscc2019Points1.EUGENE_DRAKE
                                        ),
                                        ParticipantResult(
                                                position = 4,
                                                participant = TestParticipants.Thscc2019Points1.JIMMY_MCKENZIE
                                        )
                                ),
                                TestGroupings.THSCC_2019_GS to listOf(
                                        ParticipantResult(
                                                position = 2,
                                                participant = TestParticipants.Thscc2019Points1.TERI_POTTER
                                        ),
                                        ParticipantResult(
                                                position = 3,
                                                participant = TestParticipants.Thscc2019Points1.NORMAN_ROBINSON
                                        )
                                )
                        )
                )
        )
}