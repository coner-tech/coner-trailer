package tech.coner.trailer

import java.nio.file.Paths
import java.time.LocalDate

object TestEvents {

    object Lscc2019 {

        val points1 by lazy { event(
            name = "2019 LSCC Points Event #1",
            date = LocalDate.of(2019, 3, 3),
            policy = TestPolicies.lsccV1,
        ) }
        val points2 by lazy { event(
            name = "2019 LSCC Points Event #2",
            date = LocalDate.of(2019, 4, 4),
            policy = TestPolicies.lsccV1,
        ) }
        val points3 by lazy { event(
            name = "2019 LSCC Points Event #3",
            date = LocalDate.of(2019, 5, 11),
            policy = TestPolicies.lsccV1,
        ) }
        val points4 by lazy { event(
            name = "2019 LSCC Points Event #4",
            date = LocalDate.of(2019, 6, 22),
            policy = TestPolicies.lsccV1,
        ) }
        val points5 by lazy { event(
            name = "2019 LSCC Points Event #5",
            date = LocalDate.of(2019, 6, 23),
            policy = TestPolicies.lsccV1,
        ) }
        val points6 by lazy { event(
            name = "2019 LSCC Points Event #6",
            date = LocalDate.of(2019, 7, 28),
            policy = TestPolicies.lsccV1,
        ) }
        val points7 by lazy { event(
            name = "2019 LSCC Points Event #7",
            date = LocalDate.of(2019, 8, 31),
            policy = TestPolicies.lsccV1,
        ) }
        val points8 by lazy { event(
            name = "2019 LSCC Points Event #8",
            date = LocalDate.of(2019, 9, 28),
            policy = TestPolicies.lsccV1,
        ) }
        val points9 by lazy { event(
            name = "2019 LSCC Points Event #9",
            date = LocalDate.of(2019, 10, 27),
            policy = TestPolicies.lsccV1,
        ) }
    }

    object Lscc2019Simplified {
        val points1 by lazy { event(
            name = "2019 LSCC Simplified Points Event #1",
            date = LocalDate.parse("2019-01-01"),
            policy = TestPolicies.lsccV1,
        ) }
        val points2 by lazy { event(
            name = "2019 LSCC Simplified Points Event #2",
            date = LocalDate.parse("2019-02-02"),
            policy = TestPolicies.lsccV1,
        ) }
        val points3 by lazy { event(
            name = "2019 LSCC Simplified Points Event #3",
            date = LocalDate.parse("2019-03-03"),
            policy = TestPolicies.lsccV1,
        ) }
    }

    object Lscc2022 {
        val points3 by lazy {
            event(
                name = "2022 LSCC Points Autocross #3",
                date = LocalDate.parse("2022-05-15"),
                policy = TestPolicies.lsccV1
            )
        }
    }

}

private fun event(
    name: String,
    date: LocalDate,
    lifecycle: Event.Lifecycle = Event.Lifecycle.FINAL,
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
    eventControlFile = Paths.get("$eventName.ecf"),
    classDefinitionFile = Paths.get("$eventName.def"),
    peopleMap = emptyMap()
)