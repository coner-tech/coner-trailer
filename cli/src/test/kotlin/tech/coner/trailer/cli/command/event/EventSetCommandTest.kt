package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.io.service.EventService
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.createFile
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.view.EventViewRenderer

@ExtendWith(MockKExtension::class)
class EventSetCommandTest : BaseDataSessionCommandTest<EventSetCommand>() {

    private val service: EventService by instance()
    private val view: EventViewRenderer by instance(Format.TEXT)

    private lateinit var crispyFish: Path

    override fun createCommand(di: DI, global: GlobalModel) = EventSetCommand(di, global)

    override fun postSetup() {
        super.postSetup()
        crispyFish = global.requireEnvironment().requireDatabaseConfiguration().crispyFishDatabase
    }

    @Test
    fun `It should set event properties`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val original = TestEvents.Lscc2019.points1
        val set = original.copy(
            name = "It should set event properties",
            date = LocalDate.parse("2020-12-07"),
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = crispyFish.resolve("set-event-control-file.ecf")
                    .createFile(),
                classDefinitionFile = crispyFish.resolve("set-class-definition-file.ecf")
                    .createFile(),
                peopleMap = emptyMap()
            ),
            motorsportReg = Event.MotorsportRegMetadata(
                id = "motorsportreg-event-id"
            )
        )
        coEvery { service.findByKey(original.id) } returns Result.success(original)
        coJustRun { service.update(any()) }
        val viewRendered = "view rendered set event named: ${set.name}"
        every { view(any()) } returns viewRendered

        command.parse(arrayOf(
            "${original.id}",
            "--name", set.name,
            "--date", "${set.date}",
            "--crispy-fish", "set",
            "--event-control-file", "${set.requireCrispyFish().eventControlFile}",
            "--class-definition-file", "${set.requireCrispyFish().classDefinitionFile}",
            "--motorsportreg", "set",
            "--msr-event-id", "${set.motorsportReg?.id}"
        ))

        coVerifySequence {
            service.findByKey(original.id)
            service.update(set)
            view(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should keep event properties for options not passed`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val original = TestEvents.Lscc2019.points1
        val crispyFish = checkNotNull(original.crispyFish) { "Expected event.crispyFish to be not null" }
        coEvery { service.findByKey(original.id) } returns Result.success(original)
        coJustRun {
            service.update(original)
        }
        val viewRendered = "view rendered set event named: ${original.name}"
        every { view(eq(original)) } returns viewRendered

        command.parse(arrayOf(
            "${original.id}",
        ))

        coVerifySequence {
            service.findByKey(original.id)
            service.update(original)
            view(original)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

}