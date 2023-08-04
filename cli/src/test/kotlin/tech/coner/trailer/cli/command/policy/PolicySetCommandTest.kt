package tech.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView

class PolicySetCommandTest : BaseDataSessionCommandTest<PolicySetCommand>() {

    private val service: PolicyService by instance()
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
        every { service.findById(original.id) } returns original
        justRun { service.update(set) }
        val viewRender = "view rendered"
//        every { view(set) } returns viewRender

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
//            view(set)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRender)
    }
}