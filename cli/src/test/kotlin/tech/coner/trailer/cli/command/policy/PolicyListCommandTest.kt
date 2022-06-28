package tech.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.PolicyService

@ExtendWith(MockKExtension::class)
class PolicyListCommandTest : DIAware {

    lateinit var command: PolicyListCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: PolicyService by instance()
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = PolicyListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list policies`() {
        val policies = listOf(TestPolicies.lsccV1, TestPolicies.lsccV2)
        every { service.list() } returns policies
        val renderedV1 = "rendered v1"
        every { view.render(TestPolicies.lsccV1) } returns renderedV1
        val renderedV2 = "rendered v2"
        every { view.render(TestPolicies.lsccV2) } returns renderedV2

        command.parse(emptyArray())

        assertThat(testConsole.output, "console output").isEqualTo("$renderedV1\n$renderedV2")
        verifySequence {
            service.list()
            view.render(TestPolicies.lsccV1)
            view.render(TestPolicies.lsccV2)
        }
    }
}