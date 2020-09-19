package org.coner.trailer.cli.command

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.coner.trailer.cli.service.StubService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class StubCommandTest {

    lateinit var command: StubCommand

    @MockK
    lateinit var stubService: StubService

    lateinit var di: DI

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this)
        di = DI {
            bind<StubService>() with instance(stubService)
        }

        command = StubCommand(di)
    }

    @Test
    fun `It should run and not blow up`() {
        every { stubService.doSomething() } answers { Unit }

        command.parse(emptyArray())

        verify { stubService.doSomething() }
    }
}
