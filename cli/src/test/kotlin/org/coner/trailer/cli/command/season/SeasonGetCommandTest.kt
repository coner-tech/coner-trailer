package org.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonView
import org.coner.trailer.io.service.SeasonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonGetCommandTest {

    lateinit var command: SeasonGetCommand

    @MockK lateinit var service: SeasonService
    @MockK lateinit var view: SeasonView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        val di = DI {
            bind<SeasonService>() with instance(service)
            bind<SeasonView>() with instance(view)
        }
        console = StringBufferConsole()
        command = SeasonGetCommand(
                di = di,
                useConsole = console
        )
    }

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
        assertThat(console.output).isEqualTo(viewRendered)
    }

}