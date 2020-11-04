package org.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.MotorsportRegMemberTableView
import org.coner.trailer.client.motorsportreg.model.Member
import org.coner.trailer.io.service.MotorsportRegMemberService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class MotorsportRegMemberListCommandTest {

    lateinit var command: MotorsportRegMemberListCommand

    @MockK lateinit var service: MotorsportRegMemberService
    @MockK lateinit var view: MotorsportRegMemberTableView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        command = MotorsportRegMemberListCommand(
                di = DI {
                    bind<MotorsportRegMemberService>() with instance(service)
                    bind<MotorsportRegMemberTableView>() with instance(view)
                },
                useConsole = console
        )
    }

    @Test
    fun `It should list members`() {
        val members = listOf(mockk<Member>())
        every { service.list() } returns members
        val viewRendersMembers = "view renders members"
        every { view.render(members) } returns viewRendersMembers

        command.parse(arrayOf())

        verifySequence {
            service.list()
            view.render(any())
        }
        assertThat(console.output, "console output").isEqualTo(viewRendersMembers)
    }
}