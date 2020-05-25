package org.coner.trailer

import java.awt.event.TextEvent

object TestSeasonEvents {

    object LSCC_2019 {
        val POINTS1: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS1, eventNumber = 1)
        val POINTS2: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS2, eventNumber = 2)
        val POINTS3: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS3, eventNumber = 3)
        val POINTS4: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS4, eventNumber = 4)
        val POINTS5: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS5, eventNumber = 5)
        val POINTS6: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS6, eventNumber = 6)
        val POINTS7: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS7, eventNumber = 7)
        val POINTS8: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS8, eventNumber = 8)
        val POINTS9: SeasonEvent
            get() = points(event = TestEvents.THSCC_2019_POINTS9, eventNumber = 9)

        private fun points(event: Event, eventNumber: Int) = SeasonEvent(
                event = event,
                eventNumber = eventNumber,
                seasonPointsCalculatorConfigurationModel = null,
                points = true
        )
    }
}