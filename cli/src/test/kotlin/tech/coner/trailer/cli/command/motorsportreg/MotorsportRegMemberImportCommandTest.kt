package tech.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.render.view.PersonCollectionViewRenderer

class MotorsportRegMemberImportCommandTest : BaseDataSessionCommandTest<MotorsportRegMemberImportCommand>() {

    private val service: MotorsportRegImportService by instance()
    private val view: PersonCollectionViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = MotorsportRegMemberImportCommand(di, global)

    @Test
    fun `It should actually import members as people`() {
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk()),
                created = listOf(mockk())
        )
        every { service.importMembersAsPeople(false) } returns result
        val viewRenderedCreated = "view rendered created"
        every { view.render(result.created) } returns viewRenderedCreated
        val viewRenderedUpdated = "view rendered updated"
        every { view.render(result.updated) } returns viewRenderedUpdated

        command.parse(arrayOf())

        verifySequence {
            service.importMembersAsPeople(false)
            view.render(result.created)
            view.render(result.updated)
        }
        assertThat(testConsole.output).isEqualTo("""
            Created: (1)
            $viewRenderedCreated
            
            Updated: (1)
            $viewRenderedUpdated
        """.trimIndent())
    }

    @Test
    fun `It should dry-run import members as people`() {
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk()),
                created = listOf(mockk())
        )
        every { service.importMembersAsPeople(true) } returns result
        val viewRenderedCreated = "view rendered created"
        every { view.render(result.created) } returns viewRenderedCreated
        val viewRenderedUpdated = "view rendered updated"
        every { view.render(result.updated) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "--dry-run"
        ))

        verifySequence {
            service.importMembersAsPeople(true)
            view.render(result.created)
            view.render(result.updated)
        }
        assertThat(testConsole.output).isEqualTo("""
            Created: (1)
            $viewRenderedCreated
            
            Updated: (1)
            $viewRenderedUpdated
        """.trimIndent())
    }
}