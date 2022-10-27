package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.PersonView
import tech.coner.trailer.io.service.PersonService

class PersonGetCommandTest : BaseDataSessionCommandTest<PersonGetCommand>() {

    private val service: PersonService by instance()
    private val view: PersonView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = PersonGetCommand(di, global)

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
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}