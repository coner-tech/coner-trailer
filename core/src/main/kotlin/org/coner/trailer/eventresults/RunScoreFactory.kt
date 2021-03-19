package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Time

interface RunScoreFactory {
    fun score(grouping: Grouping, time: Time, penalty: Score.Penalty?): Score
}