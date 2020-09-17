package org.coner.trailer.cli.command.person

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
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
import java.util.function.Predicate

@ExtendWith(MockKExtension::class)
class PersonSearchCommandTest {

    lateinit var command: PersonSearchCommand

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
        command = PersonSearchCommand(
                di = di,
                useConsole = console
        )
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
                "--member-id-equals", "${person.memberId}",
                "--first-name-equals", person.firstName,
                "--last-name-equals", person.lastName
        ))

        verifySequence {
            service.search(any())
            view.render(searchResults)
        }
        assertThat(console.output, "console output").isEqualTo(viewRendered)
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
                "--member-id-contains", "${person.memberId?.substring(0..3)}",
                "--first-name-contains", person.firstName.substring(0..3),
                "--last-name-contains", person.lastName.substring(0..3)
        ))

        verifySequence {
            service.search(any())
            view.render(searchResults)
        }
        assertThat(console.output, "console output").isEqualTo(viewRendered)
        val filter = serviceSearchSlot.captured
        assertThat(filter.test(person), "filter matches person").isTrue()
        assertThat(filter.test(wrongPerson), "filter doesn't match wrong person").isFalse()

    }
}