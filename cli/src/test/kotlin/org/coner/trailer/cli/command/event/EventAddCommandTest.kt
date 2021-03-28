package org.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.Event
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.DatabaseConfiguration
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PolicyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile


@ExperimentalPathApi
@ExtendWith(MockKExtension::class)
class EventAddCommandTest {

    lateinit var command: EventAddCommand

    @MockK lateinit var dbConfig: DatabaseConfiguration
    @MockK lateinit var service: EventService
    @MockK lateinit var view: EventView
    @MockK lateinit var policyService: PolicyService

    lateinit var testConsole: StringBufferConsole

    @TempDir lateinit var root: Path
    lateinit var crispyFishDatabase: Path
    lateinit var notCrispyFishDatabase: Path

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = EventAddCommand(
            di = DI {
                bind<DatabaseConfiguration>() with instance(dbConfig)
                bind<EventService>() with instance(service)
                bind<EventView>() with instance(view)
                bind<PolicyService>() with instance(policyService)
            }
        ).apply {
            context {
                console = testConsole
            }
        }
        crispyFishDatabase = root.resolve("crispy-fish").createDirectory()
        notCrispyFishDatabase = root.resolve("not-crispy-fish").createDirectory()
    }

    @Test
    fun `It should create event`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val eventControlFile = crispyFishDatabase.resolve("event.ecf").createFile()
        val classDefinitionFile = crispyFishDatabase.resolve("class.def").createFile()
        val crispyFish = Event.CrispyFishMetadata(
            eventControlFile = crispyFishDatabase.relativize(eventControlFile).toString(),
            classDefinitionFile = crispyFishDatabase.relativize(classDefinitionFile).toString(),
            peopleMap = emptyMap()
        )
        val motorsportReg = Event.MotorsportRegMetadata(
            id = "motorsportreg-event-id"
        )
        val create = TestEvents.Lscc2019.points1.copy(
            crispyFish = crispyFish,
            motorsportReg = motorsportReg,
            lifecycle = Event.Lifecycle.CREATE
        )
        every { dbConfig.crispyFishDatabase } returns crispyFishDatabase
        every { policyService.findById(create.policy.id) } returns create.policy
        justRun { service.create(any()) }
        val viewRendered = "view rendered ${create.id} with crispy fish ${create.crispyFish}"
        every { view.render(any()) } returns viewRendered

        command.parse(arrayOf(
            "--id", "${create.id}",
            "--name", create.name,
            "--date", "${create.date}",
            "--crispy-fish-event-control-file", "$eventControlFile",
            "--crispy-fish-class-definition-file", "$classDefinitionFile",
            "--motorsportreg-event-id", motorsportReg.id,
            "--policy-id", "${create.policy.id}"
        ))

        verifySequence {
            service.create(create = create)
            view.render(create)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

}