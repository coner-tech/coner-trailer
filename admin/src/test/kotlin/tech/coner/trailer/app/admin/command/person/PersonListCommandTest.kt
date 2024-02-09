package tech.coner.trailer.app.admin.command.person

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class PersonListCommandTest : BaseDataSessionCommandTest<PersonListCommand>() {

    private val service: PersonService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<PersonListCommand>()

    @Test
    fun `It should list people`() {
        val people = listOf(
                TestPeople.ANASTASIA_RIGLER,
                TestPeople.BENNETT_PANTONE,
                TestPeople.BRANDY_HUFF
        )
        every { service.list() } returns people
        val viewRendered = "view rendered ${people.size} people"
        val model: PersonCollectionModel = mockk()
        every { adapter(any()) } returns model
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf())

        verifySequence {
            service.list()
            adapter(people)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}