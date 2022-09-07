package tech.coner.trailer.eventresults

import tech.coner.trailer.TestClasses
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.TestRuns
import tech.coner.trailer.eventresults.StandardEventResultsTypes.clazz
import tech.coner.trailer.eventresults.StandardEventResultsTypes.pax
import tech.coner.trailer.eventresults.StandardEventResultsTypes.raw

object TestIndividualEventResults {

    object Lscc2019 {
        val points1: IndividualEventResults by lazy {
            // punting on full individual event results for unused full-scale event
            IndividualEventResults(
                eventContext = TestEventContexts.Lscc2019.points1,
                resultsByIndividual = emptyList(),
                innerEventResultsTypes = emptyList()
            )
        }
    }

    object Lscc2019Simplified {
        private val testClasses = TestClasses.Lscc2019
        val points1: IndividualEventResults by lazy {
            val participants = TestParticipants.Lscc2019Points1Simplified
            val rawResults = TestOverallRawEventResults.Lscc2019Simplified.points1
            val paxResults = TestOverallPaxEventResults.Lscc2019Simplified.points1
            val clazzResults = TestClazzEventResults.Lscc2019Simplified.points1
            IndividualEventResults(
                eventContext = TestEventContexts.Lscc2019Simplified.points1,
                resultsByIndividual = listOf(
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

    object LsccTieBreaking {
        val points1: IndividualEventResults by lazy {
            // punting on full individual event results not relevant for case
            IndividualEventResults(
                eventContext = TestEventContexts.LsccTieBreaking.points1,
                resultsByIndividual = emptyList(),
                innerEventResultsTypes = emptyList()
            )
        }
        val points2: IndividualEventResults by lazy {
            IndividualEventResults(
                // punting on full individual event results not relevant for case
                eventContext = TestEventContexts.LsccTieBreaking.points2,
                resultsByIndividual = emptyList(),
                innerEventResultsTypes = emptyList()
            )
        }
    }

    object Lifecycles {
        private val standardInnerEventResultsTypes by lazy {
            listOf(raw, pax, clazz)
        }
        private val participants = TestParticipants.Lifecycles
        private val runs = TestRuns.Lifecycles
        private val classes = TestClasses.Lscc2019
        object Create {
            private val eventContexts = TestEventContexts.Lifecycles.Create
            private val base by lazy {
                IndividualEventResults(
                    eventContext = eventContexts.noParticipantsYet,
                    resultsByIndividual = emptyList(),
                    innerEventResultsTypes = standardInnerEventResultsTypes
                )
            }
            val noParticipantsYet by lazy {
                base.copy(
                    eventContext = eventContexts.noParticipantsYet
                )
            }
            val runsWithoutSignage by lazy {
                base.copy(
                    eventContext = eventContexts.runsWithoutSignage
                )
            }
            val runsWithoutParticipants by lazy {
                base.copy(
                    eventContext = eventContexts.runsWithoutParticipants
                )
            }
            val participantsWithoutRuns by lazy {
                base.copy(
                    eventContext = eventContexts.participantsWithoutRuns,
                    resultsByIndividual = listOf(
                        participants.REBECCA_JACKSON to mapOf(
                            raw to null,
                            pax to null,
                            clazz to null
                        ),
                        participants.JIMMY_MCKENZIE to mapOf(
                            raw to null,
                            pax to null,
                            clazz to null
                        )
                    )
                )
            }
            val someParticipantsWithSomeRuns by lazy {
                val rawResults = TestOverallRawEventResults.Lifecycles.Create.someParticipantsWithSomeRuns
                val paxResults = TestOverallPaxEventResults.Lifecycles.Create.someParticipantsWithSomeRuns
                val clazzResults = TestClazzEventResults.Lifecycles.Create.someParticipantsWithSomeRuns
                IndividualEventResults(
                    eventContext = eventContexts.someParticipantsWithSomeRuns,
                    resultsByIndividual = listOf(
                        participants.REBECCA_JACKSON to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            pax to paxResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            clazz to clazzResults.groupParticipantResults[classes.HS]?.single { it.participant == participants.REBECCA_JACKSON }
                        ),
                        participants.JIMMY_MCKENZIE to mapOf(
                            raw to null,
                            pax to null,
                            clazz to null
                        )
                    ),
                    innerEventResultsTypes = listOf(raw, pax, clazz)
                )
            }
            val someParticipantsWithAllRuns by lazy {
                val eventContext = eventContexts.someParticipantsWithAllRuns
                val rawResults = TestOverallRawEventResults.Lifecycles.Create.someParticipantsWithAllRuns
                val paxResults = TestOverallPaxEventResults.Lifecycles.Create.someParticipantsWithAllRuns
                val clazzResults = TestClazzEventResults.Lifecycles.Create.someParticipantsWithAllRuns
                base.copy(
                    eventContext = eventContext,
                    resultsByIndividual = listOf(
                        participants.REBECCA_JACKSON to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            pax to paxResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            clazz to clazzResults.groupParticipantResults[classes.HS]?.single { it.participant == participants.REBECCA_JACKSON }
                        ),
                        participants.JIMMY_MCKENZIE to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.JIMMY_MCKENZIE },
                            pax to paxResults.participantResults.single { it.participant == participants.JIMMY_MCKENZIE },
                            clazz to clazzResults.groupParticipantResults[classes.STR]?.single { it.participant == participants.JIMMY_MCKENZIE }
                        )
                    )
                )
            }
            val allParticipantsWithSomeRuns: IndividualEventResults by lazy {
                val eventContext = eventContexts.allParticipantsWithSomeRuns
                val rawResults = TestOverallRawEventResults.Lifecycles.Create.allParticipantsWithSomeRuns
                val paxResults = TestOverallPaxEventResults.Lifecycles.Create.allParticipantsWithSomeRuns
                val clazzResults = TestClazzEventResults.Lifecycles.Create.allParticipantsWithSomeRuns
                base.copy(
                    eventContext = eventContext,
                    resultsByIndividual = listOf(
                        participants.REBECCA_JACKSON to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            pax to paxResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            clazz to clazzResults.groupParticipantResults[classes.HS]?.single { it.participant == participants.REBECCA_JACKSON }
                        ),
                        participants.JIMMY_MCKENZIE to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.JIMMY_MCKENZIE },
                            pax to paxResults.participantResults.single { it.participant == participants.JIMMY_MCKENZIE },
                            clazz to clazzResults.groupParticipantResults[classes.STR]?.single { it.participant == participants.JIMMY_MCKENZIE }
                        )
                    )
                )
            }
            val allParticipantsWithAllRuns: IndividualEventResults by lazy {
                val eventContext = eventContexts.allParticipantsWithAllRuns
                val rawResults = TestOverallRawEventResults.Lifecycles.Create.allParticipantsWithAllRuns
                val paxResults = TestOverallPaxEventResults.Lifecycles.Create.allParticipantsWithAllRuns
                val clazzResults = TestClazzEventResults.Lifecycles.Create.allParticipantsWithAllRuns
                base.copy(
                    eventContext = eventContext,
                    resultsByIndividual = listOf(
                        participants.REBECCA_JACKSON to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            pax to paxResults.participantResults.single { it.participant == participants.REBECCA_JACKSON },
                            clazz to clazzResults.groupParticipantResults[classes.HS]?.single { it.participant == participants.REBECCA_JACKSON }
                        ),
                        participants.JIMMY_MCKENZIE to mapOf(
                            raw to rawResults.participantResults.single { it.participant == participants.JIMMY_MCKENZIE },
                            pax to paxResults.participantResults.single { it.participant == participants.JIMMY_MCKENZIE },
                            clazz to clazzResults.groupParticipantResults[classes.STR]?.single { it.participant == participants.JIMMY_MCKENZIE }
                        )
                    )
                )
            }
        }
        object Pre {
            private val eventContexts = TestEventContexts.Lifecycles.Pre
            val noParticipantsYet: IndividualEventResults by lazy {
                Create.noParticipantsYet.copy(
                    eventContext = eventContexts.noParticipantsYet
                )
            }
            val runsWithoutSignage: IndividualEventResults by lazy {
                Create.runsWithoutSignage.copy(
                    eventContext = eventContexts.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: IndividualEventResults by lazy {
                Create.runsWithoutParticipants.copy(
                    eventContext = eventContexts.runsWithoutParticipants
                )
            }
            val participantsWithoutRuns: IndividualEventResults by lazy {
                Create.participantsWithoutRuns.copy(
                    eventContext = eventContexts.participantsWithoutRuns
                )
            }
            val someParticipantsWithSomeRuns: IndividualEventResults by lazy {
                Create.someParticipantsWithSomeRuns.copy(
                    eventContext = eventContexts.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: IndividualEventResults by lazy {
                Create.someParticipantsWithAllRuns.copy(
                    eventContext = eventContexts.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: IndividualEventResults by lazy {
                Create.allParticipantsWithSomeRuns.copy(
                    eventContext = eventContexts.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: IndividualEventResults by lazy {
                Create.allParticipantsWithAllRuns.copy(
                    eventContext = eventContexts.allParticipantsWithAllRuns
                )
            }
        }
        object Active {
            private val eventContexts = TestEventContexts.Lifecycles.Active
            val noParticipantsYet: IndividualEventResults by lazy {
                Create.noParticipantsYet.copy(
                    eventContext = eventContexts.noParticipantsYet
                )
            }
            val runsWithoutSignage: IndividualEventResults by lazy {
                Create.runsWithoutSignage.copy(
                    eventContext = eventContexts.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: IndividualEventResults by lazy {
                Create.runsWithoutParticipants.copy(
                    eventContext = eventContexts.runsWithoutParticipants
                )
            }
            val participantsWithoutRuns: IndividualEventResults by lazy {
                Create.participantsWithoutRuns.copy(
                    eventContext = eventContexts.participantsWithoutRuns
                )
            }
            val someParticipantsWithSomeRuns: IndividualEventResults by lazy {
                Create.someParticipantsWithSomeRuns.copy(
                    eventContext = eventContexts.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: IndividualEventResults by lazy {
                Create.someParticipantsWithAllRuns.copy(
                    eventContext = eventContexts.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: IndividualEventResults by lazy {
                Create.allParticipantsWithSomeRuns.copy(
                    eventContext = eventContexts.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: IndividualEventResults by lazy {
                Create.allParticipantsWithAllRuns.copy(
                    eventContext = eventContexts.allParticipantsWithAllRuns
                )
            }
        }
        object Post {
            private val eventContexts = TestEventContexts.Lifecycles.Post
            val noParticipantsYet: IndividualEventResults by lazy {
                Create.noParticipantsYet.copy(
                    eventContext = eventContexts.noParticipantsYet,
                    resultsByIndividual = emptyList()
                )
            }
            val runsWithoutSignage: IndividualEventResults by lazy {
                Create.runsWithoutSignage.copy(
                    eventContext = eventContexts.runsWithoutSignage,
                    resultsByIndividual = emptyList()
                )
            }
            val runsWithoutParticipants: IndividualEventResults by lazy {
                Create.runsWithoutParticipants.copy(
                    eventContext = eventContexts.runsWithoutParticipants,
                    resultsByIndividual = emptyList()
                )
            }
            val participantsWithoutRuns: IndividualEventResults by lazy {
                Create.participantsWithoutRuns.copy(
                    eventContext = eventContexts.participantsWithoutRuns,
                    resultsByIndividual = emptyList()
                )
            }
            val someParticipantsWithSomeRuns: IndividualEventResults by lazy {
                Create.someParticipantsWithSomeRuns.copy(
                    eventContext = eventContexts.someParticipantsWithSomeRuns,
                    resultsByIndividual = Create.someParticipantsWithSomeRuns.resultsByIndividual
                        .toMutableList()
                        .apply { removeIf { (participant, _) -> participant == participants.JIMMY_MCKENZIE } }
                )
            }
            val someParticipantsWithAllRuns: IndividualEventResults by lazy {
                Create.someParticipantsWithAllRuns.copy(
                    eventContext = eventContexts.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: IndividualEventResults by lazy {
                Create.allParticipantsWithSomeRuns.copy(
                    eventContext = eventContexts.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: IndividualEventResults by lazy {
                Create.allParticipantsWithAllRuns.copy(
                    eventContext = eventContexts.allParticipantsWithAllRuns
                )
            }
        }
    }

}