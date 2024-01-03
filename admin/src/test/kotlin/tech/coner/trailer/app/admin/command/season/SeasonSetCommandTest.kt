package tech.coner.trailer.app.admin.command.season

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.SeasonView
import tech.coner.trailer.io.service.SeasonService

class SeasonSetCommandTest : BaseDataSessionCommandTest<SeasonSetCommand>() {

    private val service: SeasonService by instance()
    private val view: SeasonView by instance()

    override fun DirectDI.createCommand() = instance<SeasonSetCommand>()

    @Test
    fun `It should set a season name`() {
        val season = TestSeasons.lscc2019.copy(
                seasonEvents = emptyList() // https://github.com/caeos/coner-trailer/issues/27
        )
        val update = season.copy(
                name = "renamed"
        )
        every { service.findById(season.id) } returns season
        justRun { service.update(update) }
        val viewRendered = "view rendered"
        every { view.render(update) } returns viewRendered

        val testResult = command.test(arrayOf(
                "${update.id}",
                "--name", update.name
        ))

        verifySequence {
            service.findById(update.id)
            service.update(update)
            view.render(update)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}