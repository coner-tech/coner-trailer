package org.coner.trailer

import java.time.LocalDate

object TestEvents {

    object Lscc2019 {

        val points1 by lazy { Event(
            name = "2019 LSCC Points Event #1",
            date = LocalDate.of(2019, 3, 3),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = crispyFish("2019 LSCC Points Event #1"),
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points2 by lazy { Event(
            name = "2019 LSCC Points Event #2",
            date = LocalDate.of(2019, 4, 4),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points3 by lazy { Event(
            name = "2019 LSCC Points Event #3",
            date = LocalDate.of(2019, 5, 11),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points4 by lazy { Event(
            name = "2019 LSCC Points Event #4",
            date = LocalDate.of(2019, 6, 22),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points5 by lazy { Event(
            name = "2019 LSCC Points Event #5",
            date = LocalDate.of(2019, 6, 23),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points6 by lazy { Event(
            name = "2019 LSCC Points Event #6",
            date = LocalDate.of(2019, 7, 28),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points7 by lazy { Event(
            name = "2019 LSCC Points Event #7",
            date = LocalDate.of(2019, 8, 31),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points8 by lazy { Event(
            name = "2019 LSCC Points Event #8",
            date = LocalDate.of(2019, 9, 28),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points9 by lazy { Event(
            name = "2019 LSCC Points Event #9",
            date = LocalDate.of(2019, 10, 27),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
    }

    object Lscc2019Simplified {
        val points1 by lazy { Event(
            name = "2019 LSCC Simplified Points Event #1",
            date = LocalDate.parse("2019-01-01"),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points2 by lazy { Event(
            name = "2019 LSCC Simplified Points Event #2",
            date = LocalDate.parse("2019-02-02"),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
        val points3 by lazy { Event(
            name = "2019 LSCC Simplified Points Event #3",
            date = LocalDate.parse("2019-03-03"),
            lifecycle = Event.Lifecycle.FINAL,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV1,
        ) }
    }

}

private fun event(
    name: String,
    date: LocalDate,
    lifecycle: Event.Lifecycle,
    policy: Policy
) = Event(
    name = name,
    date = date,
    lifecycle = lifecycle,
    crispyFish = crispyFish(eventName = name),
    motorsportReg = null,
    policy = policy
)

private fun crispyFish(eventName: String) = Event.CrispyFishMetadata(
    eventControlFile = "$eventName.ecf",
    classDefinitionFile = "$eventName.def",
    peopleMap = emptyMap()
)