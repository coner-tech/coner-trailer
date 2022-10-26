package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
import tech.coner.trailer.io.service.PersonService

@ExtendWith(MockKExtension::class)
class PersonListCommandTest : DIAware {

    lateinit var command: PersonListCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkServiceModule())
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
        command = PersonListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list people`() {
        val people = listOf(
                TestPeople.ANASTASIA_RIGLER,
                TestPeople.BENNETT_PANTONE,
                TestPeople.BRANDY_HUFF
        )
        every { service.list() } returns people
        val viewRendered = "view rendered ${people.size} people"
        every { view.render(people) } returns viewRendered

        command.parse(arrayOf())

        verifySequence {
            service.list()
            view.render(people)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}