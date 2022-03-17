package tech.coner.trailer

object TestSeasonEvents {

    object Lscc2019 {
        val points1: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points1, eventNumber = 1)
        val points2: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points2, eventNumber = 2)
        val points3: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points3, eventNumber = 3)
        val points4: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points4, eventNumber = 4)
        val points5: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points5, eventNumber = 5)
        val points6: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points6, eventNumber = 6)
        val points7: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points7, eventNumber = 7)
        val points8: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points8, eventNumber = 8)
        val points9: SeasonEvent
            get() = points(event = TestEvents.Lscc2019.points9, eventNumber = 9)
    }

    object Lscc2019Simplified {
        val points1 by lazy { points(
                event = TestEvents.Lscc2019Simplified.points1,
                eventNumber = 1
        ) }
        val points2 by lazy { points(
                event = TestEvents.Lscc2019Simplified.points2,
                eventNumber = 2
        ) }
        val points3 by lazy { points(
                event = TestEvents.Lscc2019Simplified.points3,
                eventNumber = 3
        ) }
    }

    object LsccTieBreaking {
        val points1: SeasonEvent get() = points(
                event = TestEvents.Lscc2019.points1,
                eventNumber = 1
        )
        val points2: SeasonEvent get() = points(
                event = TestEvents.Lscc2019.points2,
                eventNumber = 2
        )
    }

    private fun points(event: Event, eventNumber: Int) = SeasonEvent(
            event = event,
            eventNumber = eventNumber,
            seasonPointsCalculatorConfiguration = null,
            points = true
    )
}