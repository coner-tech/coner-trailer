package org.coner.trailer.eventresults

import io.mockk.every
import io.mockk.mockk
import org.coner.trailer.Participant
import org.coner.trailer.Time
import java.math.BigDecimal

fun mockkParticipantResult(
        position: Int,
        score: BigDecimal = BigDecimal("123.456"),
        participant: Participant,
        marginOfVictory: Time? = null,
        marginOfLoss: Time? = null,
        scoredRuns: List<ResultRun>? = null,
        personalBestRun: ResultRun? = null
) : ParticipantResult {
    return mockk {
        every { this@mockk.position }.returns(position)
        every { this@mockk.participant }.returns(participant)
        every { this@mockk.marginOfVictory }.returns(marginOfVictory)
        every { this@mockk.marginOfLoss }.returns(marginOfLoss)
        every { this@mockk.scoredRuns }.returns(scoredRuns ?: emptyList())
        every { this@mockk.personalBestRun }.returns(personalBestRun)
    }
}