package org.coner.trailer.seasonpoints

import org.coner.trailer.TestGroupings
import org.coner.trailer.TestPeople
import org.coner.trailer.TestSeasonEvents

object TestStandingsReports {

    val THSCC_2019_FINAL: StandingsReport
        get() {
            return StandingsReport(
                    sections = listOf(
                            StandingsReport.Section(
                                    title = TestGroupings.THSCC_2019_NOV.abbreviation,
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.DOMINIC_ROGERS,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 1,
                                                            TestSeasonEvents.LSCC_2019.POINTS2 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 1,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS8 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 9
                                                    ),
                                                    score = 41
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.BRANDY_HUFF,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 3,
                                                            TestSeasonEvents.LSCC_2019.POINTS2 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 9
                                                    ),
                                                    score = 30
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.BRYANT_MORAN,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 1,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 1,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 3,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS7 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 1
                                                    ),
                                                    score = 25
                                            )
                                    )
                            ),
                            StandingsReport.Section(
                                    title = TestGroupings.THSCC_2019_STR.abbreviation,
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.REBECCA_JACKSON,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS2 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS7 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 3
                                                    ),
                                                    score = 57
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.JIMMY_MCKENZIE,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 3,
                                                            TestSeasonEvents.LSCC_2019.POINTS2 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS7 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 4
                                                    ),
                                                    score = 34
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.EUGENE_DRAKE,
                                                    eventToPoints =  mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 3,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 1,
                                                            TestSeasonEvents.LSCC_2019.POINTS8 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 2
                                                    ),
                                                    score = 22
                                            )
                                    )
                            ),
                            StandingsReport.Section(
                                    title = TestGroupings.THSCC_2019_GS.abbreviation,
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.TERI_POTTER,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS2 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 3,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS8 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 9
                                                    ),
                                                    score = 57
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.HARRY_WEBSTER,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS7 to 9,
                                                            TestSeasonEvents.LSCC_2019.POINTS8 to 4
                                                    ),
                                                    score = 35
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.NORMAN_ROBINSON,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS1 to 2,
                                                            TestSeasonEvents.LSCC_2019.POINTS3 to 2,
                                                            TestSeasonEvents.LSCC_2019.POINTS4 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS5 to 4,
                                                            TestSeasonEvents.LSCC_2019.POINTS6 to 2,
                                                            TestSeasonEvents.LSCC_2019.POINTS7 to 6,
                                                            TestSeasonEvents.LSCC_2019.POINTS8 to 3,
                                                            TestSeasonEvents.LSCC_2019.POINTS9 to 4
                                                    ),
                                                    score = 27
                                            )
                                    )
                            )
                    )
            )
        }
}