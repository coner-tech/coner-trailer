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
import tech.coner.trailer.app.admin.view.SeasonView
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

        val testResult = command.test(arrayOf(
                "--name", get.name
        ))

        verifySequence {
            service.findByName(get.name)
            view.render(get)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

}