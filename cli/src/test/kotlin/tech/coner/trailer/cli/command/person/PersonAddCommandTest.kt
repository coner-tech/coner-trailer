package tech.coner.trailer.cli.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.text.view.TextPersonViewRenderer
import tech.coner.trailer.io.mapper.PersonMapper
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.render.view.PersonViewRenderer

class PersonAddCommandTest : BaseDataSessionCommandTest<PersonAddCommand>() {

    private val service: PersonService by instance()
    private val view: PersonViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = PersonAddCommand(di, global)

    @Test
    fun `It should add person`() {
        val person = TestPeople.ANASTASIA_RIGLER
        justRun { service.create(eq(person)) }
        val viewRendered = "view rendered ${person.lastName} ${person.lastName}"
        every { view.render(eq(person)) } returns viewRendered

        command.parse(arrayOf(
                "--id", person.id.toString(),
                "--club-member-id", "${person.clubMemberId}",
                "--first-name", person.firstName,
                "--last-name", person.lastName,
                "--motorsportreg-member-id", "${person.motorsportReg?.memberId}"
        ))

        verifySequence {
            service.create(eq(person))
            view.render(eq(person))
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}