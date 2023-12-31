package tech.coner.trailer.app.admin.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.model.PolicyCollection
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class PolicyListCommandTest : BaseDataSessionCommandTest<PolicyListCommand>() {

    private val service: PolicyService by instance()
    private val adapter: Adapter<PolicyCollection, PolicyCollectionModel> by instance()
    private val view: TextCollectionView<PolicyModel, PolicyCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<PolicyListCommand>()

    @Test
    fun `It should list policies`() {
        val policies = PolicyCollection(listOf(TestPolicies.lsccV1, TestPolicies.lsccV2))
        every { service.list() } returns policies
        val model: PolicyCollectionModel = mockk()
        every { adapter(any()) } returns model
        val viewRender = "rendered policies"
        every { view(any()) } returns viewRender

        command.parse(emptyArray())

        assertThat(testConsole.output, "console output").isEqualTo(viewRender)
        verifySequence {
            service.list()
            adapter(policies)
            view(model)
        }
    }
}