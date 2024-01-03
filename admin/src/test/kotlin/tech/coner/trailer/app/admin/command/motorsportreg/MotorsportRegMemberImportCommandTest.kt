package tech.coner.trailer.app.admin.command.motorsportreg

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
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class MotorsportRegMemberImportCommandTest : BaseDataSessionCommandTest<MotorsportRegMemberImportCommand>() {

    private val service: MotorsportRegImportService by instance()
    private val adapter: Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<MotorsportRegMemberImportCommand>()

    @Test
    fun `It should actually import members as people`() {
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk(), mockk()),
                created = listOf(mockk())
        )
        every { service.importMembersAsPeople(false) } returns result
        val createdModel: PersonCollectionModel = mockk()
        every { adapter(result.created) } returns createdModel
        val viewRenderedCreated = "view rendered created"
        every { view(createdModel) } returns viewRenderedCreated
        val updatedModel: PersonCollectionModel = mockk()
        every { adapter(result.updated) } returns updatedModel
        val viewRenderedUpdated = "view rendered updated"
        every { view(updatedModel) } returns viewRenderedUpdated

        val testResult = command.test(arrayOf())

        verifySequence {
            service.importMembersAsPeople(false)
            adapter(result.created)
            view(createdModel)
            adapter(result.updated)
            view(updatedModel)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo("""
                Created: (1)
                $viewRenderedCreated
                
                Updated: (2)
                $viewRenderedUpdated
        """.trimIndent())
        }
    }

    @Test
    fun `It should dry-run import members as people`() {
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk(), mockk()),
                created = listOf(mockk())
        )
        every { service.importMembersAsPeople(true) } returns result
        val createdModel: PersonCollectionModel = mockk()
        every { adapter(result.created) } returns createdModel
        val viewRenderedCreated = "view rendered created"
        every { view(createdModel) } returns viewRenderedCreated
        val updatedModel: PersonCollectionModel = mockk()
        every { adapter(result.updated) } returns updatedModel
        val viewRenderedUpdated = "view rendered updated"
        every { view(updatedModel) } returns viewRenderedUpdated

        val testResult = command.test(arrayOf(
                "--dry-run"
        ))

        verifySequence {
            service.importMembersAsPeople(true)
            adapter(result.created)
            view(createdModel)
            adapter(result.updated)
            view(updatedModel)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo("""
                Created: (1)
                $viewRenderedCreated
                
                Updated: (2)
                $viewRenderedUpdated
            """.trimIndent())
        }
    }
}