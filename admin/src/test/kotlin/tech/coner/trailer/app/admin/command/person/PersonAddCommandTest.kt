package tech.coner.trailer.app.admin.command.person

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.*
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.TestPeople
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenterFactory
import tech.coner.trailer.presentation.text.view.TextView

class PersonAddCommandTest : BaseDataSessionCommandTest<PersonAddCommand>() {

    private val presenterFactory: PersonDetailPresenterFactory by factory()
    private val view: TextView<PersonDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<PersonAddCommand>()

    @Test
    fun `It should add person`() {
        val presenter = presenterFactory(PersonDetailPresenter.Argument.Create)
        val initialModel: PersonDetailModel = mockk {
            justRun { setId(any()) }
            justRun { setClubMemberId(any()) }
            justRun { setFirstName(any()) }
            justRun { setLastName(any()) }
            justRun { setMotorsportRegId(any()) }
        }
        val committedModel: PersonDetailModel = mockk()
        every { presenter.itemModel } returns initialModel
        every { presenter.commit() } returns Result.success(committedModel)
        justRun { presenter.create() }
        val person = TestPeople.ANASTASIA_RIGLER
        val viewRendered = "view rendered ${person.lastName} ${person.lastName}"
        every { view(any()) } returns viewRendered

        command.parse(arrayOf(
                "--id", person.id.toString(),
                "--club-member-id", "${person.clubMemberId}",
                "--first-name", person.firstName,
                "--last-name", person.lastName,
                "--motorsportreg-member-id", "${person.motorsportReg?.memberId}"
        ))

        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
        verifySequence {
            presenter.itemModel
            initialModel.setId(person.id)
            initialModel.setClubMemberId(person.clubMemberId!!)
            initialModel.setFirstName(person.firstName)
            initialModel.setLastName(person.lastName)
            initialModel.setMotorsportRegId(person.motorsportReg?.memberId!!)
            presenter.commit()
            presenter.create()
            view(committedModel)
        }
        confirmVerified(presenter, view, initialModel, committedModel)
    }
}