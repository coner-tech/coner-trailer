package tech.coner.trailer.app.admin.command.policy

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
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

        val testResult = command.test(arrayOf("${delete.id}"))

        verifySequence {
            service.findById(delete.id)
            service.delete(delete)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEmpty()
        }
    }
}