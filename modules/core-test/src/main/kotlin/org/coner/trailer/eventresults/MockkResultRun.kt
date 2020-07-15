package org.coner.trailer.eventresults

import io.mockk.every
import io.mockk.mockk
import org.coner.trailer.Time

fun mockkResultRun(
        time: Time? = null,
        cones: Int? = null,
        didNotFinish: Boolean = false,
        disqualified: Boolean = false,
        rerun: Boolean = false,
        personalBestRun: Boolean = false
): ResultRun {
    return mockk {
        every { this@mockk.time }.returns(time)
        every { this@mockk.cones}.returns(cones)
        every { this@mockk.didNotFinish }.returns(didNotFinish)
        every { this@mockk.disqualified }.returns(disqualified)
    }
}