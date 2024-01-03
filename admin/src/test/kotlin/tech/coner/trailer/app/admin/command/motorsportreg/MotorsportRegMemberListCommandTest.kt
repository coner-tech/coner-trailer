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
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.MotorsportRegMemberTableView
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

        val testResult = command.test(arrayOf())

        verifySequence {
            service.list()
            view.render(any())
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendersMembers)
        }
    }
}