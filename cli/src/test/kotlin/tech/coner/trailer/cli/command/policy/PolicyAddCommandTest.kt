package tech.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.kodein.di.*
import tech.coner.trailer.Policy
import tech.coner.trailer.TestClubs
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.render.view.PolicyViewRenderer

class PolicyAddCommandTest : BaseDataSessionCommandTest<PolicyAddCommand>() {

    private val service: PolicyService by instance()
    private val clubService: ClubService by instance()
    private val view: PolicyViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = PolicyAddCommand(di, global)

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
        every { view(param.policy) } returns render

        command.parse(arrayOf(
            "--id", "${param.policy.id}",
            "--name", param.policy.name,
            "--cone-penalty-seconds", "${param.policy.conePenaltySeconds}",
            "--pax-time-style", param.policy.paxTimeStyle.name.toLowerCase(),
            "--final-score-style", param.policy.finalScoreStyle.name.toLowerCase()
        ))

        verifySequence {
            service.create(param.policy)
            view(param.policy)
        }
        assertThat(testConsole.output).isEqualTo(render)
    }
}