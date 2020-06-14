package org.coner.trailer

import java.time.LocalDate

object TestEvents {

    object Lscc2019 {

        val points1 = factory(
                date = LocalDate.of(2019, 3, 3),
                name = "2019 LSCC Points Event #1"
        )
        val points2 = factory(
                date = LocalDate.of(2019, 4, 4),
                name = "2019 LSCC Points Event #2"
        )
        val points3 = factory(
                date = LocalDate.of(2019, 5, 11),
                name = "2019 LSCC Points Event #3"
        )
        val points4 = factory(
                date = LocalDate.of(2019, 6, 22),
                name = "2019 LSCC Points Event #4"
        )
        val points5 = factory(
                date = LocalDate.of(2019, 6, 23),
                name = "2019 LSCC Points Event #5"
        )
        val points6 = factory(
                date = LocalDate.of(2019, 7, 28),
                name = "2019 LSCC Points Event #6"
        )
        val points7 = factory(
                date = LocalDate.of(2019, 8, 31),
                name = "2019 LSCC Points Event #7"
        )
        val points8 = factory(
                date = LocalDate.of(2019, 9, 28),
                name = "2019 LSCC Points Event #8"
        )
        val points9 = factory(
                date = LocalDate.of(2019, 10, 27),
                name = "2019 LSCC Points Event #9"
        )
    }


    private fun factory(
            date: LocalDate,
            name: String
    ) = Event(date = date, name = name)
}