package org.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.Policy
import org.coner.trailer.TestPolicies
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.io.service.PolicyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PolicyAddCommandTest {

    lateinit var command: PolicyAddCommand

    @MockK lateinit var service: PolicyService
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = PolicyAddCommand(DI {
            bind<PolicyService>() with instance(service)
            bind<PolicyView>() with instance(view)
        }).apply {
            context {
                console = testConsole
            }
        }
    }

    enum class PolicyAddParam(val policy: Policy) {
        LSCC_V1(TestPolicies.lsccV1),
        LSCC_V2(TestPolicies.lsccV2)
    }

    @ParameterizedTest
    @EnumSource
    fun `It should add a policy`(param: PolicyAddParam) {
        justRun { service.create(param.policy) }
        val render = "view rendered"
        every { view.render(param.policy) } returns render

        command.parse(arrayOf(
            "--id", "${param.policy.id}",
            "--name", param.policy.name,
            "--cone-penalty-seconds", "${param.policy.conePenaltySeconds}",
            "--pax-time-style", param.policy.paxTimeStyle.name.toLowerCase(),
            "--final-score-style", param.policy.finalScoreStyle.name.toLowerCase()
        ))

        verifySequence {
            service.create(param.policy)
            view.render(param.policy)
        }
        assertThat(testConsole.output).isEqualTo(render)
    }
}