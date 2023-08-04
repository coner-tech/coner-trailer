package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.mockk.every
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView
import java.util.function.Predicate

class PersonSearchCommandTest : BaseDataSessionCommandTest<PersonSearchCommand>() {

    private val service: PersonService by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<PersonSearchCommand>()

    @Test
    fun `It should search with equals filters`() {
        val person = TestPeople.REBECCA_JACKSON
        val wrongPerson = TestPeople.JIMMY_MCKENZIE
        val searchResults = listOf(person)
        val serviceSearchSlot = slot<Predicate<Person>>()
        every { service.search(capture(serviceSearchSlot)) } returns searchResults
        val viewRendered = "view rendered search results"
//        every { view.render(searchResults) } returns viewRendered

        command.parse(arrayOf(
                "--club-member-id-equals", "${person.clubMemberId}",
                "--first-name-equals", person.firstName,
                "--last-name-equals", person.lastName
        ))

        verifySequence {
            service.search(any())
//            view.render(searchResults)
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
//        every { view.render(searchResults) } returns viewRendered

        command.parse(arrayOf(
                "--club-member-id-contains", "${person.clubMemberId?.substring(0..3)}",
                "--first-name-contains", person.firstName.substring(0..3),
                "--last-name-contains", person.lastName.substring(0..3)
        ))

        verifySequence {
            service.search(any())
//            view.render(searchResults)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
        val filter = serviceSearchSlot.captured
        assertThat(filter.test(person), "filter matches person").isTrue()
        assertThat(filter.test(wrongPerson), "filter doesn't match wrong person").isFalse()

    }
}