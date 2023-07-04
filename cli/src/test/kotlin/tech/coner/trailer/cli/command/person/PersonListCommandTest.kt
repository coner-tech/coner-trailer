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
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.render.view.PersonCollectionViewRenderer

class PersonListCommandTest : BaseDataSessionCommandTest<PersonListCommand>() {

    private val service: PersonService by instance()
    private val view: PersonCollectionViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = PersonListCommand(di, global)

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
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}