package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Policy
import org.coner.trailer.Time
import java.math.BigDecimal

class RawTimeRunScoreFactory(
    private val policy: Policy
) : RunScoreFactory {
    override fun score(grouping: Grouping, time: Time, penalty: Score.Penalty?): Score {
        return when {
            penalty != null -> Score(penalty.floor + time.value, penalty)
        }
        fun withPenalty(scratchTime: Time, penalty: Score.Penalty): Score {
            return Score(value = penalty.floor + scratchTime.value, penalty = penalty)
        }

        fun withPenalty(scratchTime: String, penalty: Score.Penalty): Score = withPenalty(Time(scratchTime), penalty)

        fun withoutTime() = Score(value = BigDecimal.valueOf(Score.Penalty.intMaxValueTwoTenthsAsLong).setScale(3))

        fun clean(scratchTime: Time): Score {
            return Score(value = scratchTime.value)
        }

        fun clean(scratchTime: String) = clean(Time(scratchTime))
    }
}