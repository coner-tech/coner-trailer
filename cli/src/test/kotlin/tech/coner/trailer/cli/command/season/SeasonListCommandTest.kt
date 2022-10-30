package tech.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.*
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.SeasonTableView
import tech.coner.trailer.io.service.SeasonService

class SeasonListCommandTest : BaseDataSessionCommandTest<SeasonListCommand>() {

    private val service: SeasonService by instance()
    private val view: SeasonTableView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = SeasonListCommand(di, global)

    @Test
    fun `It should list seasons`() {
        val list = listOf(
                TestSeasons.lscc2019
        )
        every { service.list() } returns list
        val viewRendered = "view rendered"
        every { view.render(list) } returns viewRendered

        command.parse(emptyArray())

        verifySequence {
            service.list()
            view.render(list)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }

}