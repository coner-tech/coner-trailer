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
class PolicyGetCommandTest {

    lateinit var command: PolicyGetCommand

    @MockK lateinit var service: PolicyService
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = PolicyGetCommand(DI {
            bind<PolicyService>() with instance(service)
            bind<PolicyView>() with instance(view)
        }).apply {
            context {
                console = testConsole
            }
        }
    }

    @Test
    fun `It should get policy by id`() {
        val policy = TestPolicies.lsccV1
        every { service.findById(policy.id) } returns policy
        val viewRender = "view rendered"
        every { view.render(policy) } returns viewRender

        command.parse(arrayOf(
            "--id", "${policy.id}"
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findById(policy.id)
            view.render(policy)
        }
    }

    @Test
    fun `It should get policy by name`() {
        val policy = TestPolicies.lsccV1
        every { service.findByName(policy.name) } returns policy
        val viewRender = "view rendered"
        every { view.render(policy) } returns viewRender

        command.parse(arrayOf(
            "--name", policy.name
        ))

        assertThat(testConsole.output).isEqualTo(viewRender)
        verifySequence {
            service.findByName(policy.name)
            view.render(policy)
        }
    }
}