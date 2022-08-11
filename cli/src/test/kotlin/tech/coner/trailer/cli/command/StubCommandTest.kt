package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.context
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.service.StubService
import tech.coner.trailer.di.EnvironmentScope
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class StubCommandTest : DIAware {

    lateinit var command: StubCommand

    override val di = DI.lazy {
        import(testCliktModule)
        bind { scoped(EnvironmentScope).singleton { stubService } }
    }

    @MockK lateinit var stubService: StubService

    @TempDir lateinit var root: Path
    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        val testConfigurations = TestConfigurations(root)
        global = GlobalModel()
            .apply { environment = TestEnvironments.temporary(di, root, testConfigurations.testConfiguration(), testConfigurations.testDatabaseConfigurations.foo) }
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
