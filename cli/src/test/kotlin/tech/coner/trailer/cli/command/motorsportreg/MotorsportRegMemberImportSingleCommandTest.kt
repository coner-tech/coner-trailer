package tech.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.PersonTableView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.di.mockkMotorsportRegApiModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.MotorsportRegImportService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*

@ExtendWith(MockKExtension::class)
class MotorsportRegMemberImportSingleCommandTest : DIAware {

    lateinit var command: MotorsportRegMemberImportSingleCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        import(mockkMotorsportRegApiModule)
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: MotorsportRegImportService by instance()
    @MockK lateinit var view: PersonTableView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = MotorsportRegMemberImportSingleCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should actually import single member as person`() {
        val person = TestPeople.REBECCA_JACKSON
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk()),
                created = emptyList()
        )
        every { service.importSingleMemberAsPerson(
                motorsportRegMemberId = person.motorsportReg!!.memberId,
                dry = false
        ) } returns result
        val viewRenderedCreated = "view rendered created"
        every { view.render(result.created) } returns viewRenderedCreated
        val viewRenderedUpdated = "view rendered updated"
        every { view.render(result.updated) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "${person.motorsportReg?.memberId}"
        ))

        verifySequence {
            service.importSingleMemberAsPerson(
                    motorsportRegMemberId = person.motorsportReg!!.memberId,
                    dry = false
            )
            view.render(result.created)
            view.render(result.updated)
        }
        assertThat(testConsole.output).isEqualTo("""
            Created: (0)
            $viewRenderedCreated
            
            Updated: (1)
            $viewRenderedUpdated
        """.trimIndent())
    }

    @Test
    fun `It should dry-run import single member as person`() {
        val person = TestPeople.REBECCA_JACKSON
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = emptyList(),
                created = listOf(mockk())
        )
        every { service.importSingleMemberAsPerson(
                motorsportRegMemberId = person.motorsportReg!!.memberId,
                dry = true
        ) } returns result
        val viewRenderedCreated = "view rendered created"
        every { view.render(result.created) } returns viewRenderedCreated
        val viewRenderedUpdated = "view rendered updated"
        every { view.render(result.updated) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "${person.motorsportReg?.memberId}",
                "--dry-run"
        ))

        verifySequence {
            service.importSingleMemberAsPerson(
                    motorsportRegMemberId = person.motorsportReg!!.memberId,
                    dry = true
            )
            view.render(result.created)
            view.render(result.updated)
        }
        assertThat(testConsole.output).isEqualTo("""
            Created: (1)
            $viewRenderedCreated
            
            Updated: (0)
            $viewRenderedUpdated
        """.trimIndent())
    }
}