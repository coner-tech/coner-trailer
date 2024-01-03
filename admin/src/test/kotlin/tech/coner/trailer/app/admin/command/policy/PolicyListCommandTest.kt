package tech.coner.trailer.app.admin.command.policy

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
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

        val testResult = command.test(emptyArray())

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRender)
        }
        verifySequence {
            service.list()
            adapter(policies)
            view(model)
        }
    }
}