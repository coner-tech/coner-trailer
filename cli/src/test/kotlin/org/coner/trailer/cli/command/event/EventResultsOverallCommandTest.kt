package org.coner.trailer.cli.command.event

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.di.CrispyFishOverallResultsReportCreatorFactory
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.cli.view.OverallResultsReportTableView
import org.coner.trailer.eventresults.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventResultsOverallCommandTest {

    lateinit var command: EventResultsOverallCommand

    @MockK lateinit var eventService: EventService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var crispyFishOverallResultsReportCreatorFactory: CrispyFishOverallResultsReportCreatorFactory
    @MockK lateinit var reportTableView: OverallResultsReportTableView
    @MockK lateinit var reportHtmlRenderer: KotlinxHtmlOverallResultsReportRenderer
    @MockK lateinit var fileOutputResolver: FileOutputDestinationResolver
    
    @BeforeEach
    fun before() {
        command = EventResultsOverallCommand(DI {
            bind<EventService>() with instance(eventService)
            bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
        })
        TODO()
    }
}