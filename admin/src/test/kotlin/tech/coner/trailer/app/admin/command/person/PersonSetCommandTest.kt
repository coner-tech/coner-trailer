package tech.coner.trailer.app.admin.command.person

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.*
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
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextView

class PersonSetCommandTest : BaseDataSessionCommandTest<PersonSetCommand>() {

    private val service: PersonService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<Person, PersonDetailModel> by instance()
    private val view: TextView<PersonDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<PersonSetCommand>()

    @Test
    fun `It should change a person first and last name`() {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                firstName = "Carlton",
                lastName = "Whitehead",
        )
        every { service.findById(any()) } returns original
        justRun { service.update(any()) }
        val viewRendered = "view rendered ${set.firstName} ${set.lastName}"
        val model: PersonDetailModel = mockk()
        every { adapter(any()) } returns model
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
                "${original.id}",
                "--first-name", set.firstName,
                "--last-name", set.lastName,
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            adapter(set)
            view(model)
        }
        confirmVerified(service, adapter, view)
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

    @Test
    fun `It should set a person club member ID` () {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                clubMemberId = "different",
        )
        every { service.findById(any()) } returns original
        justRun { service.update(any()) }
        val viewRendered = "view rendered ${set.clubMemberId}"
        val model: PersonDetailModel = mockk()
        every { adapter(any()) } returns model
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
                "${original.id}",
                "--club-member-id", "${set.clubMemberId}"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            adapter(set)
            view(model)
        }
        confirmVerified(service, adapter, view)
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

    @Test
    fun `It should unset a person club member ID`() {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                clubMemberId = null,
        )
        every { service.findById(any()) } returns original
        justRun { service.update(any()) }
        val model: PersonDetailModel = mockk()
        val viewRendered = "view rendered memberId = ${set.clubMemberId}"
        every { adapter(any()) } returns model
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
                "${original.id}",
                "--club-member-id", "null"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            adapter(set)
            view(model)
        }
        confirmVerified(service, adapter, view)
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

    @Test
    fun `It should set a person motorsportreg member ID`() {
        val original = TestPeople.REBECCA_JACKSON
        val set = original.copy(
                motorsportReg = original.motorsportReg!!.copy(
                        memberId = "set"
                ),
        )
        every { service.findById(any()) } returns original
        justRun { service.update(any()) }
        val model: PersonDetailModel = mockk()
        every { adapter(any()) } returns model
        val viewRendered = "view rendered memberId = ${set.clubMemberId}"
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
                "${original.id}",
                "--motorsportreg-member-id", "set"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            adapter(set)
            view(model)
        }
        confirmVerified(service, adapter, view)
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

}