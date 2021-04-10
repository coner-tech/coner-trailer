package org.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestPolicies
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import org.coner.trailer.io.service.PolicyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PolicySetCommandTest {

    lateinit var command: PolicySetCommand

    @MockK lateinit var service: PolicyService
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = PolicySetCommand(DI {
            bind<PolicyService>() with instance(service)
            bind<PolicyView>() with instance(view)
        }).context {
            console = testConsole
        }
    }

    @Test
    fun `It should update policy`() {
        val original = TestPolicies.lsccV2
        val set = original.copy(
            name = "set weird policy",
            conePenaltySeconds = 3,
            finalScoreStyle = FinalScoreStyle.AUTOCROSS,
            paxTimeStyle = PaxTimeStyle.FAIR
        )
        every { service.findById(original.id) } returns original
        justRun { service.update(set) }
        val viewRender = "view rendered"
        every { view.render(set) } returns viewRender

        command.parse(arrayOf(
            "${original.id}",
            "--name", set.name,
            "--cone-penalty-seconds", "${set.conePenaltySeconds}",
            "--pax-time-style", set.paxTimeStyle.name.toLowerCase(),
            "--final-score-style", set.finalScoreStyle.name.toLowerCase()
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            view.render(set)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRender)
    }
}