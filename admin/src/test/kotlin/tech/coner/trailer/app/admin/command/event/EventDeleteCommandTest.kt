package tech.coner.trailer.app.admin.command.event

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestEvents
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

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            service.findByKey(event.id)
            service.delete(event)
        }
    }
}