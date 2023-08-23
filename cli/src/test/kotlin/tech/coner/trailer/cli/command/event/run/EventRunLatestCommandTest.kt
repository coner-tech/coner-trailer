package tech.coner.trailer.cli.command.event.run

import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.messageContains
import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.Format
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.render.RunRenderer

class EventRunLatestCommandTest : BaseDataSessionCommandTest<EventRunLatestCommand>() {

    private val eventService: EventService by instance()
    private val eventContextService: EventContextService by instance()
    private val renderer: RunRenderer by instance { Format.TEXT }

    private val rendered = "rendered"

    override fun createCommand(di: DI, global: GlobalModel) = EventRunLatestCommand(di, global)

    @Test
    fun `It should echo 5 latest runs by default`() {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        arrangeValidEvent(eventContext)

        command.parse(arrayOf("${eventContext.event.id}"))

        assertThat(testConsole).all {
            output().isEqualTo(rendered)
            error().isEmpty()
        }
        coVerifySequence {
            eventService.findByKey(eventContext.event.id)
            eventContextService.load(eventContext.event)
            renderer.render(eventContext.runs.takeLast(5))
        }
        confirmVerified(eventService, eventContextService, renderer)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 4, 8, 16, 32, 64])
    fun `It should echo specified latest runs`(count: Int) {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        arrangeValidEvent(eventContext)

        command.parse(arrayOf(
            "--count", "$count",
            "${eventContext.event.id}"
        ))

        assertThat(testConsole).all {
            output().isEqualTo(rendered)
            error().isEmpty()
        }
        coVerifySequence {
            eventService.findByKey(eventContext.event.id)
            eventContextService.load(eventContext.event)
            renderer.render(eventContext.runs.takeLast(count))
        }
        confirmVerified(eventService, eventContextService, renderer)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1, -2, -4, -8, -16, -32, -64])
    fun `It should fail to validate when specified count is less than 1`(count: Int) {
        val eventContext = TestEventContexts.Lscc2019Simplified.points1
        arrangeValidEvent(eventContext)

        assertFailure {
            command.parse(arrayOf(
                "--count", "$count",
                "${eventContext.event.id}"
            ))
        }
            .isInstanceOf(BadParameterValue::class)
            .all {
                messageContains("--count")
                messageContains("Count must be greater than or equal to 1")
            }

        assertThat(testConsole).all {
            output().isEmpty()
            error().isEmpty()
        }
        confirmVerified(eventService, eventContextService, renderer)
    }

    private fun arrangeValidEvent(eventContext: EventContext) {
        coEvery { eventService.findByKey(any()) } returns Result.success(eventContext.event)
        coEvery { eventContextService.load(any()) } returns Result.success(eventContext)
        every { renderer.render(runs = any()) } returns rendered
    }
}