package org.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonTableView
import org.coner.trailer.io.service.SeasonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonListCommandTest {

    lateinit var command: SeasonListCommand

    @MockK lateinit var service: SeasonService
    @MockK lateinit var view: SeasonTableView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        val di = DI {
            bind<SeasonService>() with instance(service)
            bind<SeasonTableView>() with instance(view)
        }
        console = StringBufferConsole()
        command = SeasonListCommand(
                di = di,
                useConsole = console
        )
    }

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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }

}