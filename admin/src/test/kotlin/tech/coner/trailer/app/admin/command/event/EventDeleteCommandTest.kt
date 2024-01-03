package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService

class EventDeleteCommandTest : BaseDataSessionCommandTest<EventDeleteCommand>() {

    private val service: EventService by instance()

    override fun DirectDI.createCommand() = instance<EventDeleteCommand>()

    @Test
    fun `It should delete event`() {
        val event = TestEvents.Lscc2019.points1
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        coJustRun { service.delete(event) }

        val testResult = command.test(arrayOf("${event.id}"))

        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEmpty()
        }
        coVerifySequence {
            service.findByKey(event.id)
            service.delete(event)
        }
    }
}