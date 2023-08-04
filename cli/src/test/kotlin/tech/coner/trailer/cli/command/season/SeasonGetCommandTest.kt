package tech.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.io.service.SeasonService

class SeasonGetCommandTest : BaseDataSessionCommandTest<SeasonGetCommand>() {

    private val service: SeasonService by instance()
    private val view: SeasonView by instance()

    override fun DirectDI.createCommand() = instance<SeasonGetCommand>()

    @Test
    fun `It should get a season`() {
        val get = TestSeasons.lscc2019
        every { service.findByName(get.name) } returns get
        val viewRendered = "view rendered"
        every { view.render(get) } returns viewRendered

        command.parse(arrayOf(
                "--name", get.name
        ))

        verifySequence {
            service.findByName(get.name)
            view.render(get)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

}