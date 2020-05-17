package org.coner.trailer.seasonpoints

import org.coner.trailer.Event
import org.coner.trailer.TestPeople
import org.coner.trailer.TestSeasonEvents

object TestStandingsReports {

    val THSCC_2019_FINAL: StandingsReport
        get() {
            return StandingsReport(
                    sections = listOf(
                            StandingsReport.Section(
                                    name = "NOV",
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.DOMINIC_ROGERS,
                                                    eventNumberToPoints = mapOf(
                                                            1 to 1,
                                                            2 to 6,
                                                            3 to 1,
                                                            4 to 9,
                                                            5 to 6,
                                                            8 to 9,
                                                            9 to 9
                                                    ),
                                                    score = 41
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.BRANDY_HUFF,
                                                    eventNumberToPoints = mapOf(
                                                            1 to 3,
                                                            2 to 9,
                                                            3 to 9,
                                                            5 to 9
                                                    ),
                                                    score = 30
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.BRYANT_MORAN,
                                                    eventNumberToPoints = mapOf(
                                                            1 to 1,
                                                            3 to 1,
                                                            4 to 3,
                                                            5 to 4,
                                                            6 to 6,
                                                            7 to 9,
                                                            9 to 1
                                                    ),
                                                    score = 25
                                            )
                                    )
                            ),
                            StandingsReport.Section(
                                    name = "STR",
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.REBECCA_JACKSON,
                                                    eventNumberToPoints = mapOf(
                                                            2 to 9,
                                                            3 to 9,
                                                            4 to 9,
                                                            5 to 9,
                                                            6 to 9,
                                                            7 to 9,
                                                            9 to 3
                                                    ),
                                                    score = 57
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.JIMMY_MCKENZIE,
                                                    eventNumberToPoints = mapOf(
                                                            1 to 3,
                                                            2 to 4,
                                                            3 to 4,
                                                            4 to 6,
                                                            5 to 6,
                                                            6 to 4,
                                                            7 to 6,
                                                            9 to 4
                                                    ),
                                                    score = 34
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.EUGENE_DRAKE,
                                                    eventNumberToPoints =  mapOf(
                                                            1 to 6,
                                                            3 to 3,
                                                            4 to 4,
                                                            6 to 1,
                                                            8 to 6,
                                                            9 to 2
                                                    ),
                                                    score = 22
                                            )
                                    )
                            ),
                            StandingsReport.Section(
                                    name = "GS",
                                    standings = listOf(
                                            StandingsReport.Standing(
                                                    position = 1,
                                                    person = TestPeople.TERI_POTTER,
                                                    eventNumberToPoints = mapOf(
                                                            1 to 6,
                                                            2 to 9,
                                                            3 to 3,
                                                            4 to 9,
                                                            5 to 9,
                                                            6 to 9,
                                                            8 to 6,
                                                            9 to 9
                                                    ),
                                                    score = 57
                                            ),
                                            StandingsReport.Standing(
                                                    position = 2,
                                                    person = TestPeople.HARRY_WEBSTER,
                                                    eventNumberToPoints = mapOf(
                                                            3 to 6,
                                                            4 to 6,
                                                            5 to 6,
                                                            6 to 4,
                                                            7 to 9,
                                                            8 to 4
                                                    ),
                                                    score = 35
                                            ),
                                            StandingsReport.Standing(
                                                    position = 3,
                                                    person = TestPeople.NORMAN_ROBINSON,
                                                    eventNumberToPoints = mapOf(
                                                            1 to 4,
                                                            1 to 2,
                                                            3 to 2,
                                                            4 to 4,
                                                            5 to 4,
                                                            6 to 2,
                                                            7 to 6,
                                                            8 to 3,
                                                            9 to 4
                                                    ),
                                                    score = 27
                                            )
                                    )
                            )
                    )
            )
        }
}