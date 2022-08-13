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

    object LifecycleCases {
        object Create {
            val noParticipantsYet: Event by lazy {
                factory("noParticipantsYet", Event.Lifecycle.CREATE)
            }
            val participantsWithoutRuns: Event by lazy {
                factory("participantsWithoutRuns", Event.Lifecycle.CREATE)
            }
            val participantsWithRuns: Event by lazy {
                factory("participantsWithRuns", Event.Lifecycle.CREATE)
            }
        }
        object Pre {
            val noParticipantsYet: Event by lazy {
                factory("noParticipantsYet", Event.Lifecycle.PRE)
            }
            val withParticipantsWithoutRuns: Event by lazy {
                factory("withParticipantsWithoutRuns", Event.Lifecycle.PRE)
            }
            val withParticipantsWithRuns: Event by lazy {
                factory("withParticipantsWithRuns", Event.Lifecycle.PRE)
            }
        }
        object Active {
            val noParticipants: Event by lazy {
                factory("noParticipants", Event.Lifecycle.ACTIVE)
            }
            val noRunsYet: Event by lazy {
                factory("noRunsYet", Event.Lifecycle.ACTIVE)
            }
            val someParticipantsWithSomeRuns: Event by lazy {
                factory("someParticipantsWithSomeRuns", Event.Lifecycle.ACTIVE)
            }
            val allParticipantsWithSomeRuns: Event by lazy {
                factory("allParticipantsWithSomeRuns", Event.Lifecycle.ACTIVE)
            }
            val allParticipantsWithAllRuns: Event by lazy {
                factory("allParticipantsWithAllRuns", Event.Lifecycle.ACTIVE)
            }
        }
        object Post {
            val noParticipants: Event by lazy {
                factory("noParticipants", Event.Lifecycle.POST)
            }
            val noRuns: Event by lazy {
                factory("noRuns", Event.Lifecycle.POST)
            }
            val someParticipantsWithoutRuns: Event by lazy {
                factory("someParticipantsWithoutRuns", Event.Lifecycle.POST)
            }
            val allParticipantsWithSomeRuns: Event by lazy {
                factory("allParticipantsWithSomeRuns", Event.Lifecycle.POST)
            }
            val allParticipantsWithAllRuns: Event by lazy {
                factory("allParticipantsWithAllRuns", Event.Lifecycle.POST)
            }
        }
        object Final {
            val someParticipantsWithoutRuns: Event by lazy {
                factory("someParticipantsWithoutRuns", Event.Lifecycle.FINAL)
            }
            val allParticipantsWithSomeRuns: Event by lazy {
                factory("allParticipantsWithSomeRuns", Event.Lifecycle.FINAL)
            }
            val allParticipantsWithAllRuns: Event by lazy {
                factory("allParticipantsWithAllRuns", Event.Lifecycle.FINAL)
            }
        }

        private fun factory(
            nameSuffix: String,
            lifecycle: Event.Lifecycle
        ) = Event(
            name = "Lifecycle Case: ${lifecycle.name} $nameSuffix",
            date = LocalDate.parse("2022-08-13"),
            lifecycle = lifecycle,
            crispyFish = null,
            motorsportReg = null,
            policy = TestPolicies.lsccV2
        )
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