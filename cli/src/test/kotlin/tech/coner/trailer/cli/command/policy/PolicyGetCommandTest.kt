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
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.PolicyService

@ExtendWith(MockKExtension::class)
class PolicyGetCommandTest : DIAware {

    lateinit var command: PolicyGetCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkServiceModule())
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
        command = PolicyGetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should get policy by id`() {
        val policy = TestPolicies.lsccV1
        every { service.findById(policy.id) } returns policy
        val viewRender = "view rendered"
        every { view.render(policy) } returns viewRender

        command.parse(arrayOf(
            "--id", "${policy.id}"
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findById(policy.id)
            view.render(policy)
        }
    }

    @Test
    fun `It should get policy by name`() {
        val policy = TestPolicies.lsccV1
        every { service.findByName(policy.name) } returns policy
        val viewRender = "view rendered"
        every { view.render(policy) } returns viewRender

        command.parse(arrayOf(
            "--name", policy.name
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findByName(policy.name)
            view.render(policy)
        }
    }
}