package tech.coner.trailer.cli.command.policy

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.PolicyService

@ExtendWith(MockKExtension::class)
class PolicySetCommandTest : DIAware {

    lateinit var command: PolicySetCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkServiceModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: PolicyService by instance()
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = PolicySetCommand(di, global)
            .context { console = testConsole }
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