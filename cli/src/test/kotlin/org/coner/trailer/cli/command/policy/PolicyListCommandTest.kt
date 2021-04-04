package org.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestPolicies
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.io.service.PolicyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PolicyListCommandTest {

    lateinit var command: PolicyListCommand

    @MockK lateinit var service: PolicyService
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = PolicyListCommand(DI {
            bind<PolicyService>() with instance(service)
            bind<PolicyView>() with instance(view)
        })
        command.context {
            console = testConsole
        }
    }

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