package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import tech.coner.trailer.Person
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
class PersonSetCommandTest : DIAware {

    lateinit var command: PersonSetCommand

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
        command = PersonSetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should change a person first and last name`() {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                firstName = "Carlton",
                lastName = "Whitehead",
        )
        every { service.findById(any()) } returns original
        every { service.update(any()) } answers { Unit }
        val viewRendered = "view rendered ${set.firstName} ${set.lastName}"
        every { view.render(any<Person>()) } returns viewRendered

        command.parse(arrayOf(
                "${original.id}",
                "--first-name", set.firstName,
                "--last-name", set.lastName,
        ))

        verifySequence {
            service.findById(original.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }

    @Test
    fun `It should set a person club member ID` () {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                clubMemberId = "different",
        )
        every { service.findById(any()) } returns original
        every { service.update(any()) } answers { Unit }
        val viewRendered = "view rendered ${set.clubMemberId}"
        every { view.render(any<Person>()) } returns viewRendered

        command.parse(arrayOf(
                "${original.id}",
                "--club-member-id", "${set.clubMemberId}"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }

    @Test
    fun `It should unset a person club member ID`() {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                clubMemberId = null,
        )
        every { service.findById(any()) } returns original
        every { service.update(any()) } answers { Unit }
        val viewRendered = "view rendered memberId = ${set.clubMemberId}"
        every { view.render(any<Person>()) } returns viewRendered

        command.parse(arrayOf(
                "${original.id}",
                "--club-member-id", "null"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }

    @Test
    fun `It should set a person motorsportreg member ID`() {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                motorsportReg = original.motorsportReg!!.copy(
                        memberId = "set"
                ),
        )
        every { service.findById(any()) } returns original
        every { service.update(any()) } answers { Unit }
        val viewRendered = "view rendered memberId = ${set.clubMemberId}"
        every { view.render(any<Person>()) } returns viewRendered

        command.parse(arrayOf(
                "${original.id}",
                "--motorsportreg-member-id", "set"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }

}