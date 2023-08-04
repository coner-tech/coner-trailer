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
import tech.coner.trailer.io.model.PolicyCollection
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class PolicyListCommandTest : BaseDataSessionCommandTest<PolicyListCommand>() {

    private val service: PolicyService by instance()
    private val view: TextCollectionView<PolicyModel, PolicyCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<PolicyListCommand>()

    @Test
    fun `It should list policies`() {
        val policies = listOf(TestPolicies.lsccV1, TestPolicies.lsccV2)
        every { service.list() } returns policies
        val viewRender = "rendered policies"
//        every { view(policies) } returns viewRender

        command.parse(emptyArray())

        assertThat(testConsole.output, "console output").isEqualTo(viewRender)
        verifySequence {
            service.list()
//            view(policies)
        }
    }
}