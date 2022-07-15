package tech.coner.trailer

object TestRuns {

    object Lscc2019Simplified {

        val points1 by lazy {
            val participants = TestParticipants.Lscc2019Points1Simplified
            listOf(
                testRun(1, participants.EUGENE_DRAKE, Time("49.367")),
                testRun(2, participants.EUGENE_DRAKE, Time("49.230")),
                testRun(3, participants.EUGENE_DRAKE, Time("48.807")),
                testRun(4, participants.EUGENE_DRAKE, Time("49.573"), didNotFinish = true),
                testRun(5, participants.EUGENE_DRAKE, Time("47.544")),
                testRun(6, participants.JIMMY_MCKENZIE, Time("50.115"), cones = 2),
                testRun(7, participants.JIMMY_MCKENZIE, Time("50.162"), cones = 1),
                testRun(8, participants.JIMMY_MCKENZIE, Time("49.672")),
                testRun(9, participants.JIMMY_MCKENZIE, Time("49.992")),
                testRun(10, participants.JIMMY_MCKENZIE, Time("48.723")),
                testRun(11, participants.REBECCA_JACKSON, Time("52.749")),
                testRun(12, participants.REBECCA_JACKSON, Time("53.175")),
                testRun(13, participants.REBECCA_JACKSON, Time("52.130")),
                testRun(14, participants.REBECCA_JACKSON, Time("52.117")),
                testRun(15, participants.REBECCA_JACKSON, Time("51.408")),
                testRun(16, participants.ANASTASIA_RIGLER, Time("53.693")),
                testRun(17, participants.ANASTASIA_RIGLER, Time("52.179"), cones = 1),
                testRun(18, participants.ANASTASIA_RIGLER, Time("52.256")),
                testRun(19, participants.ANASTASIA_RIGLER, Time("51.323")),
                testRun(20, participants.ANASTASIA_RIGLER, Time("51.344")),
                testRun(21, participants.BRANDY_HUFF, Time("49.419"), cones = 4),
                testRun(22, participants.BRANDY_HUFF, Time("49.848"), cones = 3),
                testRun(23, participants.BRANDY_HUFF, Time("48.515")),
                testRun(24, participants.BRANDY_HUFF, Time("49.076"), cones = 1),
                testRun(25, participants.BRANDY_HUFF, Time("49.436")),
                testRun(26, participants.BRYANT_MORAN, Time("56.353"), cones = 1),
                testRun(27, participants.BRYANT_MORAN, Time("55.831")),
                testRun(28, participants.BRYANT_MORAN, Time("52.201")),
                testRun(29, participants.BRYANT_MORAN, Time("52.062"), cones = 1),
                testRun(30, participants.BRYANT_MORAN, Time("53.074")),
                testRun(31, participants.DOMINIC_ROGERS, Time("54.246")),
                testRun(32, participants.DOMINIC_ROGERS, Time("53.629"), cones = 1),
                testRun(33, participants.DOMINIC_ROGERS, Time("51.856"), cones = 1),
                testRun(34, participants.DOMINIC_ROGERS, Time("53.409")),
                testRun(35, participants.DOMINIC_ROGERS, Time("52.447"))
            )
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
}
