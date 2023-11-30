package tech.coner.trailer.app.admin.command.policy

import assertk.assertThat
import assertk.assertions.isEmpty
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PolicyService

class PolicyDeleteCommandTest : BaseDataSessionCommandTest<PolicyDeleteCommand>() {

    private val service: PolicyService by instance()

    override fun DirectDI.createCommand() = instance<PolicyDeleteCommand>()

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