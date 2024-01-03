package tech.coner.trailer.app.admin.command.policy

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.TestClubs
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView

class PolicyAddCommandTest : BaseDataSessionCommandTest<PolicyAddCommand>() {

    private val service: PolicyService by instance()
    private val clubService: ClubService by instance()
    private val adapter: Adapter<Policy, PolicyModel> by instance()
    private val view: TextView<PolicyModel> by instance()

    override fun DirectDI.createCommand() = instance<PolicyAddCommand>()

    enum class PolicyAddParam(val policy: Policy) {
        LSCC_V1(TestPolicies.lsccV1),
        LSCC_V2(TestPolicies.lsccV2)
    }

    @ParameterizedTest
    @EnumSource
    fun `It should add a policy`(param: PolicyAddParam) {
        every { clubService.get() } returns TestClubs.lscc
        justRun { service.create(param.policy) }
        val render = "view rendered"
        val model: PolicyModel = mockk()
        every { adapter(any()) } returns model
        every { view(any()) } returns render

        val testResult = command.test(arrayOf(
            "--id", "${param.policy.id}",
            "--name", param.policy.name,
            "--cone-penalty-seconds", "${param.policy.conePenaltySeconds}",
            "--pax-time-style", param.policy.paxTimeStyle.name.toLowerCase(),
            "--final-score-style", param.policy.finalScoreStyle.name.toLowerCase()
        ))

        verifySequence {
            service.create(param.policy)
            adapter(param.policy)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(render)
        }
    }
}