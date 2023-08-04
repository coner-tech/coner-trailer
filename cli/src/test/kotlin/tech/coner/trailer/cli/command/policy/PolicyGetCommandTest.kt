package tech.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView

class PolicyGetCommandTest : BaseDataSessionCommandTest<PolicyGetCommand>() {

    private val service: PolicyService by instance()
    private val view: TextView<PolicyModel> by instance()

    override fun DirectDI.createCommand() = instance<PolicyGetCommand>()

    @Test
    fun `It should get policy by id`() {
        val policy = TestPolicies.lsccV1
        every { service.findById(policy.id) } returns policy
        val viewRender = "view rendered"
//        every { view(policy) } returns viewRender

        command.parse(arrayOf(
            "--id", "${policy.id}"
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findById(policy.id)
//            view(policy)
        }
    }

    @Test
    fun `It should get policy by name`() {
        val policy = TestPolicies.lsccV1
        every { service.findByName(policy.name) } returns policy
        val viewRender = "view rendered"
//        every { view(policy) } returns viewRender

        command.parse(arrayOf(
            "--name", policy.name
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findByName(policy.name)
//            view(policy)
        }
    }
}