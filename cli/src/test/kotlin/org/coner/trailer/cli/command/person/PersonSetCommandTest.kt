package org.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.Person
import org.coner.trailer.TestPeople
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PersonSetCommandTest {

    lateinit var command: PersonSetCommand

    @MockK lateinit var service: PersonService
    @MockK lateinit var view: PersonView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        val di = DI {
            bind<PersonService>() with instance(service)
            bind<PersonView>() with instance(view)
        }
        command = PersonSetCommand(
                di = di,
                useConsole = console
        )
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
                "--last-name", set.lastName
        ))

        verifySequence {
            service.findById(original.id)
            service.update(eq(set))
            view.render(eq(set))
        }
        assertThat(console.output, "console output").isEqualTo(viewRendered)
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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }



}