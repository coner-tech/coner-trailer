package org.coner.trailer

import java.time.LocalDate

object TestEvents {

    object Lscc2019 {

        val points1: Event
            get() = Event(
                    name = "2019 LSCC Points Event #1",
                    date = LocalDate.of(2019, 3, 3),
                    crispyFish = null
            )
        val points2: Event
            get() = Event(
                    name = "2019 LSCC Points Event #2",
                    date = LocalDate.of(2019, 4, 4),
                    crispyFish = null
            )
        val points3: Event
            get() = Event(
                    name = "2019 LSCC Points Event #3",
                    date = LocalDate.of(2019, 5, 11),
                    crispyFish = null
            )
        val points4: Event
            get() = Event(
                    name = "2019 LSCC Points Event #4",
                    date = LocalDate.of(2019, 6, 22),
                    crispyFish = null
            )
        val points5: Event
            get() = Event(
                    name = "2019 LSCC Points Event #5",
                    date = LocalDate.of(2019, 6, 23),
                    crispyFish = null
            )
        val points6: Event
            get() = Event(
                    name = "2019 LSCC Points Event #6",
                    date = LocalDate.of(2019, 7, 28),
                    crispyFish = null
            )
        val points7: Event
            get() = Event(
                    name = "2019 LSCC Points Event #7",
                    date = LocalDate.of(2019, 8, 31),
                    crispyFish = null
            )
        val points8: Event
            get() = Event(
                    name = "2019 LSCC Points Event #8",
                    date = LocalDate.of(2019, 9, 28),
                    crispyFish = null
            )
        val points9: Event
            get() = Event(
                    name = "2019 LSCC Points Event #9",
                    date = LocalDate.of(2019, 10, 27),
                    crispyFish = null
            )
    }

    object Lscc2019Simplified {
        val points1 by lazy { Event(
                name = "2019 LSCC Simplified Points Event #1",
                date = LocalDate.parse("2019-01-01"),
                crispyFish = null
        ) }
        val points2 by lazy { Event(
                name = "2019 LSCC Simplified Points Event #2",
                date = LocalDate.parse("2019-02-02"),
                crispyFish = null
        ) }
        val points3 by lazy { Event(
                name = "2019 LSCC Simplified Points Event #3",
                date = LocalDate.parse("2019-03-03"),
                crispyFish = null
        ) }
    }

    object Olscc2019 {
        val points1: Event
            get() = Event(
                    name = "2019 OLSCC Points Event #1",
                    date = LocalDate.of(2019, 3, 10),
                    crispyFish = null
            )
    }

}