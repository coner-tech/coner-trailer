package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextView

class PersonAddCommandTest : BaseDataSessionCommandTest<PersonAddCommand>() {

    private val service: PersonService by instance()
    private val view: TextView<PersonDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<PersonAddCommand>()

    @Test
    fun `It should add person`() {
        val person = TestPeople.ANASTASIA_RIGLER
        justRun { service.create(eq(person)) }
        val viewRendered = "view rendered ${person.lastName} ${person.lastName}"
//        every { view.render(eq(person)) } returns viewRendered

        command.parse(arrayOf(
                "--id", person.id.toString(),
                "--club-member-id", "${person.clubMemberId}",
                "--first-name", person.firstName,
                "--last-name", person.lastName,
                "--motorsportreg-member-id", "${person.motorsportReg?.memberId}"
        ))

        verifySequence {
            service.create(eq(person))
//            view.render(eq(person))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}