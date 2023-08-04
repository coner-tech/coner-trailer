package tech.coner.trailer.cli.command.motorsportreg

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.view.MotorsportRegMemberTableView
import tech.coner.trailer.client.motorsportreg.model.Member
import tech.coner.trailer.io.service.MotorsportRegMemberService

class MotorsportRegMemberListCommandTest : BaseDataSessionCommandTest<MotorsportRegMemberListCommand>() {

    private val service: MotorsportRegMemberService by instance()
    private val view: MotorsportRegMemberTableView by instance()

    override fun DirectDI.createCommand() = instance<MotorsportRegMemberListCommand>()

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
        assertThat(testConsole.output, "console output").isEqualTo(viewRendersMembers)
    }
}