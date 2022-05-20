package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*

@ExtendWith(MockKExtension::class)
class PersonGetCommandTest : DIAware {

    lateinit var command: PersonGetCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: PersonService by instance()
    @MockK lateinit var view: PersonView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = PersonGetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should get a person`() {
        val person = TestPeople.ANASTASIA_RIGLER
        every { service.findById(person.id) } returns person
        val viewRendered = "view rendered ${person.firstName} ${person.lastName}"
        every { view.render(person) } returns viewRendered

        command.parse(arrayOf(
                person.id.toString()
        ))

        verifySequence {
            service.findById(person.id)
            view.render(person)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}