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