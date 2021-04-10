package org.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEmpty
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestPolicies
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.service.PolicyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PolicyDeleteCommandTest {

    lateinit var command: PolicyDeleteCommand

    @MockK lateinit var service: PolicyService

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = PolicyDeleteCommand(DI {
            bind<PolicyService>() with instance(service)
        })
    }

    @Test
    fun `It should delete a policy`() {
        val delete = TestPolicies.lsccV2
        every { service.findById(delete.id) } returns delete
        justRun { service.delete(delete) }

        command.parse(arrayOf("${delete.id}"))

        verifySequence {
            service.findById(delete.id)
            service.delete(delete)
        }
        assertThat(testConsole.output, "console output").isEmpty()
    }
}