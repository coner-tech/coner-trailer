package tech.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.io.service.SeasonService

class SeasonSetCommandTest : BaseDataSessionCommandTest<SeasonSetCommand>() {

    private val service: SeasonService by instance()
    private val view: SeasonView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = SeasonSetCommand(di, global)

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

        command.parse(arrayOf(
                "${update.id}",
                "--name", update.name
        ))

        verifySequence {
            service.findById(update.id)
            service.update(update)
            view.render(update)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}