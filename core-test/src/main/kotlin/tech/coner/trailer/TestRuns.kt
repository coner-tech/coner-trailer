package tech.coner.trailer

import tech.coner.trailer.TestParticipants.LifecycleCases.participants

object TestRuns {

    object Lscc2019Simplified {

        val points1 by lazy {
            with (TestParticipants.Lscc2019Points1Simplified) {
                listOf(
                    testRun(1, EUGENE_DRAKE, "49.367"),
                    testRun(2, EUGENE_DRAKE, "49.230"),
                    testRun(3, EUGENE_DRAKE, "48.807"),
                    testRun(4, EUGENE_DRAKE, "49.573", didNotFinish = true),
                    testRun(5, EUGENE_DRAKE, "47.544"),
                    testRun(6, JIMMY_MCKENZIE, "50.115", cones = 2),
                    testRun(7, JIMMY_MCKENZIE, "50.162", cones = 1),
                    testRun(8, JIMMY_MCKENZIE, "49.672"),
                    testRun(9, JIMMY_MCKENZIE, "49.992"),
                    testRun(10, JIMMY_MCKENZIE, "48.723"),
                    testRun(11, REBECCA_JACKSON, "52.749"),
                    testRun(12, REBECCA_JACKSON, "53.175"),
                    testRun(13, REBECCA_JACKSON, "52.130"),
                    testRun(14, REBECCA_JACKSON, "52.117"),
                    testRun(15, REBECCA_JACKSON, "51.408"),
                    testRun(16, ANASTASIA_RIGLER, "53.693"),
                    testRun(17, ANASTASIA_RIGLER, "52.179", cones = 1),
                    testRun(18, ANASTASIA_RIGLER, "52.256"),
                    testRun(19, ANASTASIA_RIGLER, "51.323"),
                    testRun(20, ANASTASIA_RIGLER, "51.344"),
                    testRun(21, BRANDY_HUFF, "49.419", cones = 4),
                    testRun(22, BRANDY_HUFF, "49.848", cones = 3),
                    testRun(23, BRANDY_HUFF, "48.515"),
                    testRun(24, BRANDY_HUFF, "49.076", cones = 1),
                    testRun(25, BRANDY_HUFF, "49.436"),
                    testRun(26, BRYANT_MORAN, "56.353", cones = 1),
                    testRun(27, BRYANT_MORAN, "55.831"),
                    testRun(28, BRYANT_MORAN, "52.201"),
                    testRun(29, BRYANT_MORAN, "52.062", cones = 1),
                    testRun(30, BRYANT_MORAN, "53.074"),
                    testRun(31, DOMINIC_ROGERS, "54.246"),
                    testRun(32, DOMINIC_ROGERS, "53.629", cones = 1),
                    testRun(33, DOMINIC_ROGERS, "51.856", cones = 1),
                    testRun(34, DOMINIC_ROGERS, "53.409"),
                    testRun(35, DOMINIC_ROGERS, "52.447")
                )
            }
        }
        val points2 by lazy {
           with (TestParticipants.Lscc2019Points2Simplified) {
               listOf(
                   testRun(1, DOMINIC_ROGERS, "41.111"),
                   testRun(2, DOMINIC_ROGERS, "39.404"),
                   testRun(3, DOMINIC_ROGERS, "38.698"),
                   testRun(4, DOMINIC_ROGERS, "39.324"),
                   testRun(5, BRANDY_HUFF, "37.528"),
                   testRun(6, BRANDY_HUFF, "37.058"),
                   testRun(7, BRANDY_HUFF, "37.889", cones = 1),
                   testRun(8, BRANDY_HUFF, "37.371"),
                   testRun(9, REBECCA_JACKSON, "35.542"),
                   testRun(10, REBECCA_JACKSON, "35.206"),
                   testRun(11, REBECCA_JACKSON, "35.954"),
                   testRun(12, REBECCA_JACKSON, "34.762"),
                   testRun(13, ANASTASIA_RIGLER, "40.783"),
                   testRun(14, ANASTASIA_RIGLER, "39.201"),
                   testRun(15, ANASTASIA_RIGLER, "38.986"),
                   testRun(16, ANASTASIA_RIGLER, "38.281", cones = 1),
                   testRun(17, JIMMY_MCKENZIE, "37.197", cones = 2),
                   testRun(18, JIMMY_MCKENZIE, "37.364", cones = 3),
                   testRun(19, JIMMY_MCKENZIE, "36.253"),
                   testRun(20, JIMMY_MCKENZIE, "36.185"),
                   testRun(21, BENNETT_PANTONE, "48.074", didNotFinish = true),
                   testRun(22, BENNETT_PANTONE, "59.294", didNotFinish = true),
                   testRun(23, BENNETT_PANTONE, "47.112", didNotFinish = true),
                   testRun(24, BENNETT_PANTONE, "49.503", didNotFinish = true)
               )
           }
        }
        val points3 by lazy {
            with (TestParticipants.Lscc2019Points3Simplified) {
                listOf(
                    testRun(1, DOMINIC_ROGERS, "92.097", didNotFinish = true),
                    testRun(2, DOMINIC_ROGERS, "90.314", didNotFinish = true),
                    testRun(3, DOMINIC_ROGERS, "100.651", didNotFinish = true),
                    testRun(4, DOMINIC_ROGERS, "100.215"),
                    testRun(5, BRANDY_HUFF, "88.172", cones = 1),
                    testRun(6, BRANDY_HUFF, "88.421", cones = 2),
                    testRun(7, BRANDY_HUFF, "91.091", cones = 1),
                    testRun(8, BRANDY_HUFF, "88.079", cones = 1),
                    testRun(9, BRYANT_MORAN, "91.507", didNotFinish = true),
                    testRun(10, BRYANT_MORAN, "92.884", didNotFinish = true),
                    testRun(11, BRYANT_MORAN, "96.059", cones = 2),
                    testRun(12, BRYANT_MORAN, "96.878", cones = 2),
                    testRun(13, REBECCA_JACKSON, "81.065", cones = 2),
                    testRun(14, REBECCA_JACKSON, "80.476"),
                    testRun(15, REBECCA_JACKSON, "80.444", cones = 2),
                    testRun(16, REBECCA_JACKSON, "80.854"),
                    testRun(17, ANASTASIA_RIGLER, "93.262", cones = 2),
                    testRun(18, ANASTASIA_RIGLER, "92.462"),
                    testRun(19, ANASTASIA_RIGLER, "94.836", cones = 1),
                    testRun(20, ANASTASIA_RIGLER, "92.741"),
                    testRun(21, JIMMY_MCKENZIE, "85.036", cones = 2),
                    testRun(22, JIMMY_MCKENZIE, "84.418", cones = 1),
                    testRun(23, JIMMY_MCKENZIE, "82.428", cones = 2),
                    testRun(24, JIMMY_MCKENZIE, "83.740"),
                    testRun(25, EUGENE_DRAKE, "101.622", didNotFinish = true),
                    testRun(26, EUGENE_DRAKE, "89.538"),
                    testRun(27, EUGENE_DRAKE, "87.036"),
                    testRun(28, EUGENE_DRAKE, "86.552", cones = 2),
                    testRun(29, BENNETT_PANTONE, "107.911", cones = 1),
                    testRun(30, BENNETT_PANTONE, "99.647"),
                    testRun(31, BENNETT_PANTONE, "100.489"),
                    testRun(32, BENNETT_PANTONE, "97.996", cones = 1)
                )
            }
        }
    }

    object LsccTieBreaking {

        val points1 by lazy {
            val participants = TestParticipants.LsccTieBreaking
            listOf(
                testRun(
                    sequence = 1,
                    participant = participants.TERI_POTTER.copy(seasonPointsEligible = false),
                    time = Time("45.678")
                ),
                testRun(
                    sequence = 2,
                    participant = participants.REBECCA_JACKSON,
                    time = Time("56.789")
                )
            )
        }

        val points2 by lazy {
            val participants = TestParticipants.LsccTieBreaking
            listOf(
                testRun(
                    sequence = 1,
                    participant = participants.TERI_POTTER.copy(seasonPointsEligible = false),
                    time = Time("45.678")
                ),
                testRun(
                    sequence = 2,
                    participant = participants.REBECCA_JACKSON,
                    time = Time("56.789")
                )
            )
        }
    }

    object LifecycleCases {
        private val participants by lazy { TestParticipants.LifecycleCases }

        val runsWithoutSignage: List<Run> by lazy {
            allParticipantsWithAllRuns
                .map { it.copy(signage = null, participant = null) }
        }
        val runsWithoutParticipants: List<Run> by lazy {
            allParticipantsWithAllRuns
                .map { it.copy(participant = null) }
        }
        val someParticipantsWithSomeRuns: List<Run> by lazy {
            allParticipantsWithAllRuns.subList(0, 1)
        }
        val someParticipantsWithAllRuns: List<Run> by lazy {
            mutableListOf<Run>()
                .apply {
                    addAll(
                        allParticipantsWithAllRuns.filter { it.participant == participants.REBECCA_JACKSON }
                            .also { check(it.size == 2) { "Expected only two runs for Rebecca Jackson" } }
                    )
                    add(allParticipantsWithAllRuns.first { it.participant == participants.JIMMY_MCKENZIE })
                }
                .sortedBy { it.sequence }
        }

        val allParticipantsWithAllRuns by lazy {
            listOf(
                testRun(
                    sequence = 1,
                    participant = participants.REBECCA_JACKSON,
                    time = Time("34.567")
                ),
                testRun(
                    sequence = 2,
                    participant = participants.JIMMY_MCKENZIE,
                    time = Time("35.678")
                ),
                testRun(
                    sequence = 3,
                    participant = participants.REBECCA_JACKSON,
                    time = Time("34.456")
                ),
                testRun(
                    sequence = 4,
                    participant = participants.JIMMY_MCKENZIE,
                    time = Time("35.567")
                )
            )
        }
    }
}
