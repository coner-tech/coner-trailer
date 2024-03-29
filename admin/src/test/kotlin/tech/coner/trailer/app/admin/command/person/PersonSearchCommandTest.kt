package tech.coner.trailer.app.admin.command.person

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stderr
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView
import java.util.function.Predicate

class PersonSearchCommandTest : BaseDataSessionCommandTest<PersonSearchCommand>() {

    private val service: PersonService by instance()
    private val adapter: Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<PersonSearchCommand>()

    @Test
    fun `It should search with equals filters`() {
        val person = TestPeople.REBECCA_JACKSON
        val wrongPerson = TestPeople.JIMMY_MCKENZIE
        val searchResults = listOf(person)
        val serviceSearchSlot = slot<Predicate<Person>>()
        every { service.search(capture(serviceSearchSlot)) } returns searchResults
        val model: PersonCollectionModel = mockk()
        every { adapter(any()) } returns model
        val viewRendered = "view rendered search results"
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
                "--club-member-id-equals", "${person.clubMemberId}",
                "--first-name-equals", person.firstName,
                "--last-name-equals", person.lastName
        ))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
            stderr().isEmpty()
        }
        verifySequence {
            // https://github.com/coner-tech/coner-trailer/issues/102 testing smell
            service.search(any()) // verified with filter behavior assertions below
            adapter(searchResults)
            view(model)
        }
        confirmVerified(service, adapter, view)
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
        val model: PersonCollectionModel = mockk()
        every { adapter(any()) } returns model
        val viewRendered = "view rendered search results"
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
                "--club-member-id-contains", "${person.clubMemberId?.substring(0..3)}",
                "--first-name-contains", person.firstName.substring(0..3),
                "--last-name-contains", person.lastName.substring(0..3)
        ))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
            stderr().isEmpty()
        }
        verifySequence {
            // https://github.com/coner-tech/coner-trailer/issues/102 testing smell
            service.search(any()) // verified with filter behavior assertions below
            adapter(searchResults)
            view(model)
        }
        confirmVerified(service, adapter, view)
        val filter = serviceSearchSlot.captured
        assertThat(filter.test(person), "filter matches person").isTrue()
        assertThat(filter.test(wrongPerson), "filter doesn't match wrong person").isFalse()
    }
}