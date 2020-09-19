package org.coner.trailer.datasource.crispyfish.eventsresults

import assertk.all
import assertk.assertThat
import assertk.assertions.isTrue
import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.eventresults.*
import org.junit.jupiter.api.Test

class ResultRunMapperTest {

    @Test
    fun `It should map (core) ResultRun from clean but not best`() {
        val run = RegistrationRun(
                time = "123.456",
                penalty = null
        )

        val actual = ResultRunMapper.map(
                crispyFishRegistrationRun = run, // Run #2
                crispyFishRegistrationRunIndex = 1, // Run #2
                crispyFishRegistrationBestRun = 3 // Run #3
        )

        assertThat(actual).all {
            hasTime(run.time)
            isClean()
        }
    }

    @Test
    fun `It should map (core) ResultRun from coned run`() {
        val run = RegistrationRun(
                time = "",
                penalty = RegistrationRun.Penalty.Cone(3)
        )

        val actual = ResultRunMapper.map(
                crispyFishRegistrationRun = run,
                crispyFishRegistrationRunIndex = 1,
                crispyFishRegistrationBestRun = 3
        )

        assertThat(actual).hasCones(3)
    }

    @Test
    fun `It should map (core) ResultRun from Did Not Finish run`() {
        val run = RegistrationRun(
                time = "",
                penalty = RegistrationRun.Penalty.DidNotFinish
        )

        val actual = ResultRunMapper.map(
                crispyFishRegistrationRun = run,
                crispyFishRegistrationRunIndex = 1,
                crispyFishRegistrationBestRun = 3
        )

        assertThat(actual).didNotFinish().isTrue()
    }

    @Test
    fun `It should map (core) ResultRun from Disqualified run`() {
        val run = RegistrationRun(
                time = "",
                penalty = RegistrationRun.Penalty.Disqualified
        )

        val actual = ResultRunMapper.map(
                crispyFishRegistrationRun = run,
                crispyFishRegistrationRunIndex = 1,
                crispyFishRegistrationBestRun = 3
        )

        assertThat(actual).disqualified().isTrue()
    }
}