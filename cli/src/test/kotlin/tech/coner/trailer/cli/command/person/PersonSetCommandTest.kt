package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.render.view.PersonViewRenderer

class PersonSetCommandTest : BaseDataSessionCommandTest<PersonSetCommand>() {

    private val service: PersonService by instance()
    private val view: PersonViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = PersonSetCommand(di, global)

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