package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.PersonService
import java.util.function.Predicate

@ExtendWith(MockKExtension::class)
class PersonSearchCommandTest : DIAware {

    lateinit var command: PersonSearchCommand

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
        command = PersonSearchCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should search with equals filters`() {
        val person = TestPeople.REBECCA_JACKSON
        val wrongPerson = TestPeople.JIMMY_MCKENZIE
        val searchResults = listOf(person)
        val serviceSearchSlot = slot<Predicate<Person>>()
        every { service.search(capture(serviceSearchSlot)) } returns searchResults
        val viewRendered = "view rendered search results"
        every { view.render(searchResults) } returns viewRendered

        command.parse(arrayOf(
                "--club-member-id-equals", "${person.clubMemberId}",
                "--first-name-equals", person.firstName,
                "--last-name-equals", person.lastName
        ))

        verifySequence {
            service.search(any())
            view.render(searchResults)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
        val filter = serviceSearchSlot.captured
        assertThat(filter.test(person), "filter matches person").isTrue()
        assertThat(filter.test(wrongPerson), "filter doesn't match wrong person").isFalse()
    }

    @Test
    fun `It should search with contains filters`() {
        val person = TestPeople.REBECCA_JACKSON
        val wrongPerson = TestPeople.JIMMY_MCKENZIE
        val searchResults = listOf(person)
        val serviceSearchSlot = slot<Predicate<Person>>()
        every { service.search(capture(serviceSearchSlot)) } returns searchResults
        val viewRendered = "view rendered search results"
        every { view.render(searchResults) } returns viewRendered

        command.parse(arrayOf(
                "--club-member-id-contains", "${person.clubMemberId?.substring(0..3)}",
                "--first-name-contains", person.firstName.substring(0..3),
                "--last-name-contains", person.lastName.substring(0..3)
        ))

        verifySequence {
            service.search(any())
            view.render(searchResults)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
        val filter = serviceSearchSlot.captured
        assertThat(filter.test(person), "filter matches person").isTrue()
        assertThat(filter.test(wrongPerson), "filter doesn't match wrong person").isFalse()

    }
}