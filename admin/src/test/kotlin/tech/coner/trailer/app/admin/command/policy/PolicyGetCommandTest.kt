package tech.coner.trailer.app.admin.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.Policy
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.PolicyModelAdapter
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView

class PolicyGetCommandTest : BaseDataSessionCommandTest<PolicyGetCommand>() {

    private val service: PolicyService by instance()
    private val adapter: Adapter<Policy, PolicyModel> by instance()
    private val view: TextView<PolicyModel> by instance()

    override fun DirectDI.createCommand() = instance<PolicyGetCommand>()

    @Test
    fun `It should get policy by id`() {
        val policy = TestPolicies.lsccV1
        every { service.findById(policy.id) } returns policy
        val model: PolicyModel = mockk()
        every { adapter(any()) } returns model
        val viewRender = "view rendered"
        every { view(model) } returns viewRender

        command.parse(arrayOf(
            "--id", "${policy.id}"
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findById(policy.id)
            adapter(policy)
            view(model)
        }
        confirmVerified(service, adapter, view, model)
    }

    @Test
    fun `It should get policy by name`() {
        val policy = TestPolicies.lsccV1
        every { service.findByName(policy.name) } returns policy
        val model: PolicyModel = mockk()
        every { adapter(any()) } returns model
        val viewRender = "view rendered"
        every { view(model) } returns viewRender

        command.parse(arrayOf(
            "--name", policy.name
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findByName(policy.name)
            adapter(policy)
            view(model)
        }
        confirmVerified(service, adapter, view, model)
    }
}