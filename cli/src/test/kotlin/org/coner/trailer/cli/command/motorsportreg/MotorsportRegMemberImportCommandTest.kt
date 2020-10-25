package org.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.PersonTableView
import org.coner.trailer.io.service.MotorsportRegImportService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class MotorsportRegMemberImportCommandTest {

    lateinit var command: MotorsportRegMemberImportCommand

    @MockK lateinit var service: MotorsportRegImportService
    @MockK lateinit var view: PersonTableView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        command = MotorsportRegMemberImportCommand(
                di = DI {
                    bind<MotorsportRegImportService>() with instance(service)
                    bind<PersonTableView>() with instance(view)
                },
                useConsole = console
        )
    }

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
        assertThat(console.output).isEqualTo("""
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
        assertThat(console.output).isEqualTo("""
            Created: (1)
            $viewRenderedCreated
            
            Updated: (1)
            $viewRenderedUpdated
        """.trimIndent())
    }
}