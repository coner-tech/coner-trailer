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
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.kodein.di.*
import tech.coner.trailer.Policy
import tech.coner.trailer.TestClubs
import tech.coner.trailer.TestPolicies
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.PolicyService

@ExtendWith(MockKExtension::class)
class PolicyAddCommandTest : DIAware {

    lateinit var command: PolicyAddCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: PolicyService by instance()
    private val clubService: ClubService by instance()
    @MockK lateinit var view: PolicyView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = PolicyAddCommand(di, global)
            .context { console = testConsole }
    }

    enum class PolicyAddParam(val policy: Policy) {
        LSCC_V1(TestPolicies.lsccV1),
        LSCC_V2(TestPolicies.lsccV2)
    }

    @ParameterizedTest
    @EnumSource
    fun `It should add a policy`(param: PolicyAddParam) {
        every { clubService.get() } returns TestClubs.lscc
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