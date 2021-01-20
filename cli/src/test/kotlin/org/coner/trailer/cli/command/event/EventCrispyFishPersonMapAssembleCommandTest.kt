package org.coner.trailer.cli.command.event

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.view.CrispyFishRegistrationView
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.verification.EventCrispyFishPersonMapVerifier
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapAssembleCommandTest {
    
    lateinit var command: EventCrispyFishPersonMapAssembleCommand
    
    @MockK lateinit var service: EventService
    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier
    @MockK lateinit var crispyFishRegistrationView: CrispyFishRegistrationView
    @MockK lateinit var personView: PersonView
    @MockK lateinit var crispyFishParticipantMapper: CrispyFishParticipantMapper
    @MockK lateinit var crispyFishPersonMapper: CrispyFishPersonMapper
    
    @BeforeEach
    fun before() {
        command = EventCrispyFishPersonMapAssembleCommand(
            di = DI {
                bind<EventService>() with instance(service)
                bind<PersonService>() with instance(personService)
                bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
                bind<EventCrispyFishPersonMapVerifier>() with instance(eventCrispyFishPersonMapVerifier)
                bind<CrispyFishRegistrationView>() with instance(crispyFishRegistrationView)
                bind<PersonView>() with instance(personView)
                bind<CrispyFishParticipantMapper>() with instance(crispyFishParticipantMapper)
                bind<CrispyFishPersonMapper>() with instance(crispyFishPersonMapper)
            }
        )
    }

    @Test
    fun `It should assemble person map`() {
        TODO()
    }
}