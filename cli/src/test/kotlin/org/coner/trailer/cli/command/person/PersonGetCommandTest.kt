package org.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
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
class PersonGetCommandTest {

    lateinit var command: PersonGetCommand

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
        command = PersonGetCommand(di = di, useConsole = console)
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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }
}