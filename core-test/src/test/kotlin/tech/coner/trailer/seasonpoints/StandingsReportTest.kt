package tech.coner.trailer.seasonpoints

import assertk.all
import assertk.assertThat
import assertk.assertions.messageContains
import io.mockk.every
import io.mockk.mockk
import tech.coner.trailer.SeasonEvent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StandingsReportTest {

    @Test
    fun `Its constructor should throw if any pointsEvents are non-points`() {
        val nonPointsSeasonEvent = mockk<SeasonEvent> {
            every { points }.returns(false)
        }

        val actual = assertThrows<IllegalArgumentException> {
            StandingsReport(
                    sections = emptyList(),
                    pointsEvents = listOf(nonPointsSeasonEvent)
            )
        }

        assertThat(actual).all {
            messageContains("pointsEvents")
            messageContains("true")
        }
    }

    @Test
    fun `Its constructor should not throw if all pointsEvents are points`() {
        val pointsSeasonEvent1 = mockk<SeasonEvent> {
            every { points }.returns(true)
        }

        assertDoesNotThrow {
            StandingsReport(
                    sections = emptyList(),
                    pointsEvents = listOf(pointsSeasonEvent1)
            )
        }
    }
}