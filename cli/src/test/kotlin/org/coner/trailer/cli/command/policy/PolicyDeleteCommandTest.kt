package org.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEmpty
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestPolicies
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.PolicyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PolicyDeleteCommandTest : DIAware {

    lateinit var command: PolicyDeleteCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
    }
    override val diContext = diContext { command.diContext.value }

    private val service: PolicyService by instance()

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = PolicyDeleteCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should delete a policy`() {
        val delete = TestPolicies.lsccV2
        every { service.findById(delete.id) } returns delete
        justRun { service.delete(delete) }

        command.parse(arrayOf("${delete.id}"))

        verifySequence {
            service.findById(delete.id)
            service.delete(delete)
        }
        assertThat(testConsole.output, "console output").isEmpty()
    }
}