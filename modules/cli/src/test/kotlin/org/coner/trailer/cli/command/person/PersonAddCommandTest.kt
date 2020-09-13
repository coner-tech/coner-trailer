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
import org.coner.trailer.io.mapper.PersonMapper
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class PersonAddCommandTest {

    lateinit var command: PersonAddCommand

    @MockK lateinit var service: PersonService
    @MockK lateinit var mapper: PersonMapper
    @MockK lateinit var view: PersonView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        val di = DI {
            bind<PersonService>() with instance(service)
            bind<PersonMapper>() with instance(mapper)
            bind<PersonView>() with instance(view)
        }
        command = PersonAddCommand(
                di = di,
                useConsole = console
        )
    }

    @Test
    fun `It should add person`() {
        val person = TestPeople.ANASTASIA_RIGLER
        every { service.create(eq(person)) } answers { Unit }
        val viewRendered = "view rendered ${person.lastName} ${person.lastName}"
        every { view.render(eq(person)) } returns viewRendered

        command.parse(arrayOf(
                "--id", person.id.toString(),
                "--member-id", "${person.memberId}",
                "--first-name", person.firstName,
                "--last-name", person.lastName
        ))

        verifySequence {
            service.create(eq(person))
            view.render(eq(person))
        }
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }
}