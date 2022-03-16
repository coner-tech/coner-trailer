package org.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestPeople
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.AbstractCommandTest
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.util.logging.Logger.global

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