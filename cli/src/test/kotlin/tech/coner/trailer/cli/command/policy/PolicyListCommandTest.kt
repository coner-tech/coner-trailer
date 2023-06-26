package tech.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.io.service.PolicyService

class PolicyListCommandTest : BaseDataSessionCommandTest<PolicyListCommand>() {

    private val service: PolicyService by instance()
    private val view: PolicyView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = PolicyListCommand(di, global)

    @Test
    fun `It should list policies`() {
        val policies = listOf(TestPolicies.lsccV1, TestPolicies.lsccV2)
        every { service.list() } returns policies
        val viewRender = "rendered policies"
        every { view.render(policies) } returns viewRender

        command.parse(emptyArray())

        assertThat(testConsole.output, "console output").isEqualTo(viewRender)
        verifySequence {
            service.list()
            view.render(policies)
        }
    }
}