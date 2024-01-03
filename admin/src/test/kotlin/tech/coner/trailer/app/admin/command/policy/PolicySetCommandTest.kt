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
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView

class PolicySetCommandTest : BaseDataSessionCommandTest<PolicySetCommand>() {

    private val service: PolicyService by instance()
    private val adapter: Adapter<Policy, PolicyModel> by instance()
    private val view: TextView<PolicyModel> by instance()

    override fun DirectDI.createCommand() = instance<PolicySetCommand>()

    @Test
    fun `It should update policy`() {
        val original = TestPolicies.lsccV2
        val set = original.copy(
            name = "set weird policy",
            conePenaltySeconds = 3,
            finalScoreStyle = FinalScoreStyle.AUTOCROSS,
            paxTimeStyle = PaxTimeStyle.FAIR
        )
        every { service.findById(any()) } returns original
        justRun { service.update(any()) }
        val model: PolicyModel = mockk()
        every { adapter(any()) } returns model
        val viewRender = "view rendered"
        every { view(any()) } returns viewRender

        val testResult = command.test(arrayOf(
            "${original.id}",
            "--name", set.name,
            "--cone-penalty-seconds", "${set.conePenaltySeconds}",
            "--pax-time-style", set.paxTimeStyle.name.toLowerCase(),
            "--final-score-style", set.finalScoreStyle.name.toLowerCase()
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            adapter(set)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRender)
        }
    }
}