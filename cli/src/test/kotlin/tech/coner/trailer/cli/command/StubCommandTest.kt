package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.context
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.service.StubService
import tech.coner.trailer.di.EnvironmentScope
import tech.coner.trailer.io.TestEnvironments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*

@ExtendWith(MockKExtension::class)
class StubCommandTest : DIAware {

    lateinit var command: StubCommand

    override val di = DI.lazy {
        bind { scoped(EnvironmentScope).singleton { stubService } }
    }

    lateinit var testConsole: StringBufferConsole

    @MockK lateinit var stubService: StubService

    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        global = GlobalModel()
            .apply { environment = TestEnvironments.minimal(di) }
        testConsole = StringBufferConsole()
        command = StubCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should run and not blow up`() {
        justRun { stubService.doSomething() }

        command.parse(emptyArray())

        verify { stubService.doSomething() }
    }
}
