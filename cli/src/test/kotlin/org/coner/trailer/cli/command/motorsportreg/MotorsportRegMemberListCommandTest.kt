package org.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.AbstractCommandTest
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.MotorsportRegMemberTableView
import org.coner.trailer.client.motorsportreg.model.Member
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.di.mockkMotorsportRegApiModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.MotorsportRegMemberService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.util.logging.Logger.global

@ExtendWith(MockKExtension::class)
class MotorsportRegMemberListCommandTest : DIAware {

    lateinit var command: MotorsportRegMemberListCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        import(mockkMotorsportRegApiModule)
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: MotorsportRegMemberService by instance()
    @MockK lateinit var view: MotorsportRegMemberTableView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = MotorsportRegMemberListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list members`() {
        val members = listOf(mockk<Member>())
        every { service.list() } returns members
        val viewRendersMembers = "view renders members"
        every { view.render(members) } returns viewRendersMembers

        command.parse(arrayOf())

        verifySequence {
            service.list()
            view.render(any())
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendersMembers)
    }
}