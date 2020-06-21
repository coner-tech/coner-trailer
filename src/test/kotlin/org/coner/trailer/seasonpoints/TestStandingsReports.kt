package org.coner.trailer.seasonpoints

import org.coner.trailer.TestGroupings
import org.coner.trailer.TestPeople
import org.coner.trailer.TestSeasonEvents

object TestStandingsReports {

    val lscc2019Final: StandingsReport
        get() {
            return StandingsReport(
                    sections = listOf(
                            StandingsReport.Section(
                                    title = TestGroupings.Lscc2019.NOV.abbreviation,
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.DOMINIC_ROGERS,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 1,
                                                            TestSeasonEvents.Lscc2019.points2 to 6,
                                                            TestSeasonEvents.Lscc2019.points3 to 1,
                                                            TestSeasonEvents.Lscc2019.points4 to 9,
                                                            TestSeasonEvents.Lscc2019.points5 to 6,
                                                            TestSeasonEvents.Lscc2019.points8 to 9,
                                                            TestSeasonEvents.Lscc2019.points9 to 9
                                                    ),
                                                    score = 41,
                                                    tie = false
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.BRANDY_HUFF,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 3,
                                                            TestSeasonEvents.Lscc2019.points2 to 9,
                                                            TestSeasonEvents.Lscc2019.points3 to 9,
                                                            TestSeasonEvents.Lscc2019.points5 to 9
                                                    ),
                                                    score = 30,
                                                    tie = false
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.BRYANT_MORAN,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 1,
                                                            TestSeasonEvents.Lscc2019.points3 to 1,
                                                            TestSeasonEvents.Lscc2019.points4 to 3,
                                                            TestSeasonEvents.Lscc2019.points5 to 4,
                                                            TestSeasonEvents.Lscc2019.points6 to 6,
                                                            TestSeasonEvents.Lscc2019.points7 to 9,
                                                            TestSeasonEvents.Lscc2019.points9 to 1
                                                    ),
                                                    score = 25,
                                                    tie = false
                                            )
                                    )
                            ),
                            StandingsReport.Section(
                                    title = TestGroupings.Lscc2019.STR.abbreviation,
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.REBECCA_JACKSON,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points2 to 9,
                                                            TestSeasonEvents.Lscc2019.points3 to 9,
                                                            TestSeasonEvents.Lscc2019.points4 to 9,
                                                            TestSeasonEvents.Lscc2019.points5 to 9,
                                                            TestSeasonEvents.Lscc2019.points6 to 9,
                                                            TestSeasonEvents.Lscc2019.points7 to 9,
                                                            TestSeasonEvents.Lscc2019.points9 to 3
                                                    ),
                                                    score = 57,
                                                    tie = false
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.JIMMY_MCKENZIE,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 3,
                                                            TestSeasonEvents.Lscc2019.points2 to 4,
                                                            TestSeasonEvents.Lscc2019.points3 to 4,
                                                            TestSeasonEvents.Lscc2019.points4 to 6,
                                                            TestSeasonEvents.Lscc2019.points5 to 6,
                                                            TestSeasonEvents.Lscc2019.points6 to 4,
                                                            TestSeasonEvents.Lscc2019.points7 to 6,
                                                            TestSeasonEvents.Lscc2019.points9 to 4
                                                    ),
                                                    score = 34,
                                                    tie = false
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.EUGENE_DRAKE,
                                                    eventToPoints =  mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 6,
                                                            TestSeasonEvents.Lscc2019.points3 to 3,
                                                            TestSeasonEvents.Lscc2019.points4 to 4,
                                                            TestSeasonEvents.Lscc2019.points6 to 1,
                                                            TestSeasonEvents.Lscc2019.points8 to 6,
                                                            TestSeasonEvents.Lscc2019.points9 to 2
                                                    ),
                                                    score = 22,
                                                    tie = false
                                            )
                                    )
                            ),
                            StandingsReport.Section(
                                    title = TestGroupings.Lscc2019.GS.abbreviation,
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.TERI_POTTER,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 6,
                                                            TestSeasonEvents.Lscc2019.points2 to 9,
                                                            TestSeasonEvents.Lscc2019.points3 to 3,
                                                            TestSeasonEvents.Lscc2019.points4 to 9,
                                                            TestSeasonEvents.Lscc2019.points5 to 9,
                                                            TestSeasonEvents.Lscc2019.points6 to 9,
                                                            TestSeasonEvents.Lscc2019.points8 to 6,
                                                            TestSeasonEvents.Lscc2019.points9 to 9
                                                    ),
                                                    score = 57,
                                                    tie = false
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.HARRY_WEBSTER,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points3 to 6,
                                                            TestSeasonEvents.Lscc2019.points4 to 6,
                                                            TestSeasonEvents.Lscc2019.points5 to 6,
                                                            TestSeasonEvents.Lscc2019.points6 to 4,
                                                            TestSeasonEvents.Lscc2019.points7 to 9,
                                                            TestSeasonEvents.Lscc2019.points8 to 4
                                                    ),
                                                    score = 35,
                                                    tie = false
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.NORMAN_ROBINSON,
                                                    eventToPoints = mapOf(
                                                            TestSeasonEvents.Lscc2019.points1 to 4,
                                                            TestSeasonEvents.Lscc2019.points1 to 2,
                                                            TestSeasonEvents.Lscc2019.points3 to 2,
                                                            TestSeasonEvents.Lscc2019.points4 to 4,
                                                            TestSeasonEvents.Lscc2019.points5 to 4,
                                                            TestSeasonEvents.Lscc2019.points6 to 2,
                                                            TestSeasonEvents.Lscc2019.points7 to 6,
                                                            TestSeasonEvents.Lscc2019.points8 to 3,
                                                            TestSeasonEvents.Lscc2019.points9 to 4
                                                    ),
                                                    score = 27,
                                                    tie = false
                                            )
                                    )
                            )
                    )
            )
        }
}