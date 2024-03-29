package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.createFile

@ExtendWith(MockKExtension::class)
class EventSetCommandTest : BaseDataSessionCommandTest<EventSetCommand>() {

    private val service: EventService by instance()
    private val adapter: Adapter<Event, EventDetailModel> by instance()
    private val view: TextView<EventDetailModel> by instance()

    private lateinit var crispyFish: Path

    override fun DirectDI.createCommand() = instance<EventSetCommand>()

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
        coEvery { service.update(any()) } returns Result.success(set)
        val model: EventDetailModel = mockk()
        every { adapter(any()) } returns model
        val viewRendered = "view rendered set event named: ${set.name}"
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
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
            adapter(set)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

    @Test
    fun `It should keep event properties for options not passed`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val original = TestEvents.Lscc2019.points1
        val crispyFish = checkNotNull(original.crispyFish) { "Expected event.crispyFish to be not null" }
        coEvery { service.findByKey(original.id) } returns Result.success(original)
        coEvery { service.update(original) } returns Result.success(original)
        val model: EventDetailModel = mockk()
        every { adapter(any()) } returns model
        val viewRendered = "view rendered set event named: ${original.name}"
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
            "${original.id}",
        ))

        coVerifySequence {
            service.findByKey(original.id)
            service.update(original)
            adapter(original)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }

}