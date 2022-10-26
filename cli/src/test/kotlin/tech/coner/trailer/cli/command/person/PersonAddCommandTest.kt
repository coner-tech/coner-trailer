package tech.coner.trailer.cli.command.person

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
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.mapper.PersonMapper
import tech.coner.trailer.io.service.PersonService

@ExtendWith(MockKExtension::class)
class PersonAddCommandTest : DIAware {

    lateinit var command: PersonAddCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkServiceModule)
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: PersonService by instance()
    private val mapper: PersonMapper by instance()
    @MockK lateinit var view: PersonView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = PersonAddCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should add person`() {
        val person = TestPeople.ANASTASIA_RIGLER
        justRun { service.create(eq(person)) }
        val viewRendered = "view rendered ${person.lastName} ${person.lastName}"
        every { view.render(eq(person)) } returns viewRendered

        command.parse(arrayOf(
                "--id", person.id.toString(),
                "--club-member-id", "${person.clubMemberId}",
                "--first-name", person.firstName,
                "--last-name", person.lastName,
                "--motorsportreg-member-id", "${person.motorsportReg?.memberId}"
        ))

        verifySequence {
            service.create(eq(person))
            view.render(eq(person))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}