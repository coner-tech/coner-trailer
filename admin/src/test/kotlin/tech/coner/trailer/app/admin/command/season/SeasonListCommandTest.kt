package tech.coner.trailer.app.admin.command.season

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.SeasonTableView
import tech.coner.trailer.io.service.SeasonService

class SeasonListCommandTest : BaseDataSessionCommandTest<SeasonListCommand>() {

    private val service: SeasonService by instance()
    private val view: SeasonTableView by instance()

    override fun DirectDI.createCommand() = instance<SeasonListCommand>()

    @Test
    fun `It should list seasons`() {
        val list = listOf(
                TestSeasons.lscc2019
        )
        every { service.list() } returns list
        val viewRendered = "view rendered"
        every { view.render(list) } returns viewRendered

        val testResult = command.test(emptyArray())

        verifySequence {
            service.list()
            view.render(list)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

}