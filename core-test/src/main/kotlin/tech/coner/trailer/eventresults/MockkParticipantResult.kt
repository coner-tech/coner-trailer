package tech.coner.trailer.eventresults

import io.mockk.every
import io.mockk.mockk
import tech.coner.trailer.Participant
import tech.coner.trailer.Time
import java.math.BigDecimal

fun mockkParticipantResult(
    position: Int,
    score: BigDecimal = BigDecimal("123.456"),
    participant: Participant,
    diffFirst: Time? = null,
    diffPrevious: Time? = null,
    scoredRuns: List<ResultRun>? = null,
    personalBestRun: ResultRun? = null
) : ParticipantResult {
    return mockk {
        every { this@mockk.position }.returns(position)
        every { this@mockk.participant }.returns(participant)
        every { this@mockk.diffFirst }.returns(diffFirst)
        every { this@mockk.diffPrevious }.returns(diffPrevious)
        every { this@mockk.scoredRuns }.returns(scoredRuns ?: emptyList())
        every { this@mockk.personalBestRun }.returns(personalBestRun)
    }
}