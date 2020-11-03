package org.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.TestPeople
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.PersonTableView
import org.coner.trailer.client.motorsportreg.model.TestMembers
import org.coner.trailer.io.service.MotorsportRegImportService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class MotorsportRegMemberImportSingleCommandTest {

    lateinit var command: MotorsportRegMemberImportSingleCommand

    @MockK
    lateinit var service: MotorsportRegImportService
    @MockK
    lateinit var view: PersonTableView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        command = MotorsportRegMemberImportSingleCommand(
                di = DI {
                    bind<MotorsportRegImportService>() with instance(service)
                    bind<PersonTableView>() with instance(view)
                },
                useConsole = console
        )
    }

    @Test
    fun `It should actually import single member as person`() {
        val person = TestPeople.REBECCA_JACKSON
        val result = MotorsportRegImportService.ImportMembersAsPeopleResult(
                updated = listOf(mockk()),
                created = emptyList()
        )
        every { service.importSingleMemberAsPerson(
                motorsportRegMemberId = person.motorsportReg!!.memberId,
                dry = false
        ) } returns result
        val viewRenderedCreated = "view rendered created"
        every { view.render(result.created) } returns viewRenderedCreated
        val viewRenderedUpdated = "view rendered updated"
        every { view.render(result.updated) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "${person.motorsportReg?.memberId}"
        ))

        verifySequence {
            service.importSingleMemberAsPerson(
                    motorsportRegMemberId = person.motorsportReg!!.memberId,
                    dry = false
            )
            view.render(result.created)
            view.render(result.updated)
        }
        assertThat(console.output).isEqualTo("""
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
        val viewRenderedCreated = "view rendered created"
        every { view.render(result.created) } returns viewRenderedCreated
        val viewRenderedUpdated = "view rendered updated"
        every { view.render(result.updated) } returns viewRenderedUpdated

        command.parse(arrayOf(
                "${person.motorsportReg?.memberId}",
                "--dry-run"
        ))

        verifySequence {
            service.importSingleMemberAsPerson(
                    motorsportRegMemberId = person.motorsportReg!!.memberId,
                    dry = true
            )
            view.render(result.created)
            view.render(result.updated)
        }
        assertThat(console.output).isEqualTo("""
            Created: (1)
            $viewRenderedCreated
            
            Updated: (0)
            $viewRenderedUpdated
        """.trimIndent())
    }
}