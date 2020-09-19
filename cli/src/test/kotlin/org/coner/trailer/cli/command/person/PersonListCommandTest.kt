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
class PersonListCommandTest {

    lateinit var command: PersonListCommand

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
        command = PersonListCommand(
                di = di,
                useConsole = console
        )
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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }
}