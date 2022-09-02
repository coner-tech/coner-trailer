package tech.coner.trailer

import java.nio.file.Paths
import java.time.LocalDate
import kotlin.reflect.KProperty

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

    sealed class LifecycleCases {
        protected abstract val lifecycle: Event.Lifecycle
        object Create : LifecycleCases() {
            override val lifecycle = Event.Lifecycle.CREATE
            val noParticipantsYet: Event by Factory()
            val runsWithoutSignage: Event by Factory()
            val runsWithoutParticipants: Event by Factory()
            val participantsWithoutRuns: Event by Factory()
            val someParticipantsWithSomeRuns: Event by Factory()
            val someParticipantsWithAllRuns: Event by Factory()
            val allParticipantsWithSomeRuns: Event by Factory()
            val allParticipantsWithAllRuns: Event by Factory()
        }
        object Pre : LifecycleCases() {
            override val lifecycle = Event.Lifecycle.PRE
            val noParticipantsYet: Event by Factory()
            val runsWithoutSignage: Event by Factory()
            val runsWithoutParticipants: Event by Factory()
            val participantsWithoutRuns: Event by Factory()
            val someParticipantsWithSomeRuns: Event by Factory()
            val someParticipantsWithAllRuns: Event by Factory()
            val allParticipantsWithSomeRuns: Event by Factory()
            val allParticipantsWithAllRuns: Event by Factory()
        }
        object Active : LifecycleCases() {
            override val lifecycle = Event.Lifecycle.ACTIVE
            val noParticipantsYet: Event by Factory()
            val runsWithoutSignage: Event by Factory()
            val runsWithoutParticipants: Event by Factory()
            val participantsWithoutRuns: Event by Factory()
            val someParticipantsWithSomeRuns: Event by Factory()
            val someParticipantsWithAllRuns: Event by Factory()
            val allParticipantsWithSomeRuns: Event by Factory()
            val allParticipantsWithAllRuns: Event by Factory()
        }
        object Post : LifecycleCases() {
            override val lifecycle = Event.Lifecycle.POST
            val noParticipantsYet: Event by Factory()
            val runsWithoutSignage: Event by Factory()
            val runsWithoutParticipants: Event by Factory()
            val participantsWithoutRuns: Event by Factory()
            val someParticipantsWithSomeRuns: Event by Factory()
            val someParticipantsWithAllRuns: Event by Factory()
            val allParticipantsWithSomeRuns: Event by Factory()
            val allParticipantsWithAllRuns: Event by Factory()
        }
        object Final : LifecycleCases() {
            override val lifecycle = Event.Lifecycle.FINAL
            val noParticipantsYet: Event by Factory()
            val runsWithoutSignage: Event by Factory()
            val runsWithoutParticipants: Event by Factory()
            val participantsWithoutRuns: Event by Factory()
            val someParticipantsWithSomeRuns: Event by Factory()
            val someParticipantsWithAllRuns: Event by Factory()
            val allParticipantsWithSomeRuns: Event by Factory()
            val allParticipantsWithAllRuns: Event by Factory()
        }

        private inner class Factory {
            operator fun getValue(thisRef: Any?, property: KProperty<*>): Event {
                return Event(
                    name = "Lifecycle Case: ${lifecycle.name} ${property.name}",
                    date = LocalDate.parse("2022-08-13"),
                    lifecycle = lifecycle,
                    crispyFish = null,
                    motorsportReg = null,
                    policy = TestPolicies.standardTest
                )
            }
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