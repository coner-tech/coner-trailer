package org.coner.trailer

import java.time.LocalDate

object TestEvents {

    object Lscc2019 {

        val points1: Event
            get() = Event(
                    date = LocalDate.of(2019, 3, 3),
                    name = "2019 LSCC Points Event #1"
            )
        val points2: Event
            get() = Event(
                    date = LocalDate.of(2019, 4, 4),
                    name = "2019 LSCC Points Event #2"
            )
        val points3: Event
            get() = Event(
                    date = LocalDate.of(2019, 5, 11),
                    name = "2019 LSCC Points Event #3"
            )
        val points4: Event
            get() = Event(
                    date = LocalDate.of(2019, 6, 22),
                    name = "2019 LSCC Points Event #4"
            )
        val points5: Event
            get() = Event(
                    date = LocalDate.of(2019, 6, 23),
                    name = "2019 LSCC Points Event #5"
            )
        val points6: Event
            get() = Event(
                    date = LocalDate.of(2019, 7, 28),
                    name = "2019 LSCC Points Event #6"
            )
        val points7: Event
            get() = Event(
                    date = LocalDate.of(2019, 8, 31),
                    name = "2019 LSCC Points Event #7"
            )
        val points8: Event
            get() = Event(
                    date = LocalDate.of(2019, 9, 28),
                    name = "2019 LSCC Points Event #8"
            )
        val points9: Event
            get() = Event(
                    date = LocalDate.of(2019, 10, 27),
                    name = "2019 LSCC Points Event #9"
            )
    }

    object Olscc2019 {
        val points1: Event
            get() = Event(
                    date = LocalDate.of(2019, 3, 10),
                    name = "2019 OLSCC Points Event #1"
            )
    }

}