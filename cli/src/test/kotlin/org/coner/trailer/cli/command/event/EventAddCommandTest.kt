package org.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestEvents
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi


@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventAddCommandTest {

    lateinit var command: EventAddCommand

    @MockK lateinit var dbConfig: DatabaseConfiguration
    @MockK lateinit var service: EventService
    @MockK lateinit var view: EventView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        command = EventAddCommand(
            di = DI {
                bind<DatabaseConfiguration>() with instance(dbConfig)
                bind<EventService>() with instance(service)
                bind<EventView>() with instance(view)
            },
            useConsole = console
        )
    }

    @Test
    fun `It should create event`() {
        val create = TestEvents.Lscc2019.points1
        justRun { service.create(eq(create)) }
        val viewRendered = "view rendered ${create.id}"
        every { view.render(create) } returns viewRendered

        command.parse(arrayOf(
            "--id", "${create.id}",
            "--name", create.name,
            "--date", "${create.date}",
        ))

        verifySequence {
            service.create(eq(create))
            view.render(eq(create))
        }
        assertThat(console.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should create event with crispy fish metadata`() {
        TODO()
    }

    @Test
    fun `It should not create event with crispy fish event control file outside of database`() {
        TODO()
    }

    @Test
    fun `It should not create event with crispy fish class definition file outside of database`() {
        TODO()
    }

}