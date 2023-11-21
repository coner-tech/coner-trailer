package tech.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.TestPeople
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class MotorsportRegMemberImportSingleCommandTest : BaseDataSessionCommandTest<MotorsportRegMemberImportSingleCommand>() {

    private val service: MotorsportRegImportService by instance()
    private val adapter: Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<MotorsportRegMemberImportSingleCommand>()

    @Test
    fun `It should actually import single member as person`() {
        val person = TestPeople.REBECCA_JACKSON
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk()),
                created = emptyList()
        )
        every {
            service.importSingleMemberAsPerson(
                motorsportRegMemberId = person.motorsportReg!!.memberId,
                dry = false
            )
        } returns result
        val createdModel: PersonCollectionModel = mockk()
        every { adapter(result.created) } returns createdModel
        val viewRenderedCreated = "view rendered created"
        every { view(createdModel) } returns viewRenderedCreated
        val updatedModel: PersonCollectionModel = mockk()
        every { adapter(result.updated) } returns updatedModel
        val viewRenderedUpdated = "view rendered updated"
        every { view(updatedModel) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "${person.motorsportReg?.memberId}"
        ))

        verifySequence {
            service.importSingleMemberAsPerson(
                    motorsportRegMemberId = person.motorsportReg!!.memberId,
                    dry = false
            )
            adapter(result.created)
            view(createdModel)
            adapter(result.updated)
            view(updatedModel)
        }
        assertThat(testConsole.output).isEqualTo("""
            Created: (0)
            $viewRenderedCreated
            
            Updated: (1)
            $viewRenderedUpdated
        """.trimIndent())
    }

    @Test
    fun `It should dry-run import single member as person`() {
        val person = TestPeople.REBECCA_JACKSON
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = emptyList(),
                created = listOf(mockk())
        )
        every { service.importSingleMemberAsPerson(
                motorsportRegMemberId = person.motorsportReg!!.memberId,
                dry = true
        ) } returns result
        val createdModel: PersonCollectionModel = mockk()
        every { adapter(result.created) } returns createdModel
        val viewRenderedCreated = "view rendered created"
        every { view(createdModel) } returns viewRenderedCreated
        val updatedModel: PersonCollectionModel = mockk()
        every { adapter(result.updated) } returns updatedModel
        val viewRenderedUpdated = "view rendered updated"
        every { view(updatedModel) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "${person.motorsportReg?.memberId}",
                "--dry-run"
        ))

        verifySequence {
            service.importSingleMemberAsPerson(
                    motorsportRegMemberId = person.motorsportReg!!.memberId,
                    dry = true
            )
            adapter(result.created)
            view(createdModel)
            adapter(result.updated)
            view(updatedModel)
        }
        assertThat(testConsole.output).isEqualTo("""
            Created: (1)
            $viewRenderedCreated
            
            Updated: (0)
            $viewRenderedUpdated
        """.trimIndent())
    }
}