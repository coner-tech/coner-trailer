package org.coner.trailer

import java.time.LocalDate
import java.util.*

object TestEvents {

    val THSCC_2019_POINTS1 = factory(
            date = LocalDate.of(2019, 3, 3),
            name = "2019 THSCC Points Event #1"
    )
    val THSCC_2019_POINTS2 = factory(
            date = LocalDate.of(2019, 4, 4),
            name = "2019 THSCC Points Event #2"
    )
    val THSCC_2019_POINTS3 = factory(
            date = LocalDate.of(2019, 5, 11),
            name = "2019 THSCC Points Event #3"
    )
    val THSCC_2019_POINTS4 = factory(
            date = LocalDate.of(2019, 6, 22),
            name = "2019 THSCC Points Event #4"
    )
    val THSCC_2019_POINTS5 = factory(
            date = LocalDate.of(2019, 6, 23),
            name = "2019 THSCC Points Event #5"
    )
    val THSCC_2019_POINTS6 = factory(
            date = LocalDate.of(2019, 7, 28),
            name = "2019 THSCC Points Event #6"
    )
    val THSCC_2019_POINTS7 = factory(
            date = LocalDate.of(2019, 8, 31),
            name = "2019 THSCC Points Event #7"
    )
    val THSCC_2019_POINTS8 = factory(
            date = LocalDate.of(2019, 9, 28),
            name = "2019 THSCC Points Event #8"
    )
    val THSCC_2019_POINTS9 = factory(
            date = LocalDate.of(2019, 10, 27),
            name = "2019 THSCC Points Event #9"
    )


    private fun factory(
            date: LocalDate,
            name: String
    ) = Event(date = date, name = name)
}