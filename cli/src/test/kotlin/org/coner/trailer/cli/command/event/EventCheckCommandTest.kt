package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.context
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.CrispyFishRegistrationTableView
import org.coner.trailer.cli.view.PeopleMapKeyTableView
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class EventCheckCommandTest {

    lateinit var command: EventCheckCommand

    @MockK lateinit var service: EventService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var registrationTableView: CrispyFishRegistrationTableView
    @MockK lateinit var peopleMapKeyTableView: PeopleMapKeyTableView

    lateinit var useConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        useConsole = StringBufferConsole()
        command = EventCheckCommand(DI {
            bind<EventService>() with instance(service)
            bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
            bind<CrispyFishRegistrationTableView>() with instance(registrationTableView)
            bind<PeopleMapKeyTableView>() with instance(peopleMapKeyTableView)
        }).context {
            console = useConsole
        }
    }

    @Test
    fun `It should check event and report all problems`() {
        TODO()
    }

    @Test
    fun `It should check event and finish silently if there are no problems`() {
        TODO()
    }
}