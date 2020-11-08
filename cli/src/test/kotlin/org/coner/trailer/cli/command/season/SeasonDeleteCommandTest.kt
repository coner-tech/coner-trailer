package org.coner.trailer.cli.command.season

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.service.SeasonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonDeleteCommandTest {

    lateinit var command: SeasonDeleteCommand

    @MockK lateinit var service: SeasonService

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        val di = DI {
            bind<SeasonService>() with instance(service)
        }
        console = StringBufferConsole()
        command = SeasonDeleteCommand(
                di = di,
                useConsole = console
        )
    }

    @Test
    fun `It should delete a season`() {
        val delete = TestSeasons.lscc2019
        every { service.findById(delete.id) } returns delete
        justRun { service.delete(delete) }

        command.parse(arrayOf(
                "${delete.id}"
        ))

        verifySequence {
            service.findById(delete.id)
            service.delete(delete)
        }
    }
}