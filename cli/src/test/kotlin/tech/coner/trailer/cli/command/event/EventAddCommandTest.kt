package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.payload.CreateEventPayload
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView
import kotlin.io.path.createFile

class EventAddCommandTest : BaseDataSessionCommandTest<EventAddCommand>() {

    private val service: EventService by instance()
    private val policyService: PolicyService by instance()
    private val constraints: EventPersistConstraints by instance()
    private val adapter: EventDetailModelAdapter by instance()
    private val view: TextView<EventDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<EventAddCommand>()

    @Test
    fun `It should create event`() {
        val create = TestEvents.Lscc2019.points1.copy(
            motorsportReg = Event.MotorsportRegMetadata(id = "motorsportreg-event-id")
        )
        every { policyService.findById(create.policy.id) } returns create.policy
        val crispyFishRoot = global.requireEnvironment().requireDatabaseConfiguration().crispyFishDatabase
        val crispyFishEventControlFile = crispyFishRoot.resolve(create.requireCrispyFish().eventControlFile)
            .also { it.createFile() }
        val crispyFishClassDefinitionFile = crispyFishRoot.resolve(create.requireCrispyFish().classDefinitionFile)
            .also { it.createFile() }
        coEvery {
            service.create(any())
        } returns Result.success(create)
        val presentationModel: EventDetailModel = mockk()
        every { adapter(create) } returns presentationModel
        val viewRendered = "view rendered ${create.id} with crispy fish ${create.crispyFish}"
        every { view.invoke(presentationModel) } returns viewRendered

        command.parse(
            arrayOf(
                "--id", "${create.id}",
                "--name", create.name,
                "--date", "${create.date}",
                "--crispy-fish-event-control-file", "$crispyFishEventControlFile",
                "--crispy-fish-class-definition-file", "$crispyFishClassDefinitionFile",
                "--motorsportreg-event-id", "${create.motorsportReg?.id}",
                "--policy-id", "${create.policy.id}"
            )
        )

        coVerifySequence {
            policyService.findById(create.policy.id)
            service.create(CreateEventPayload(
                id = create.id,
                name = create.name,
                date = create.date,
                crispyFishEventControlFile = create.requireCrispyFish().eventControlFile,
                crispyFishClassDefinitionFile = create.requireCrispyFish().classDefinitionFile,
                motorsportRegEventId = create.motorsportReg?.id,
                policy = create.policy
            ))
            view(adapter(create))
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}