package tech.coner.trailer.io.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.io.util.runSuspendCatching
import kotlin.coroutines.CoroutineContext

class EventContextService(
    override val coroutineContext: CoroutineContext,
    private val classService: ClassService,
    private val participantService: ParticipantService,
    private val runService: RunService,
    private val eventExtendedParametersService: EventExtendedParametersService
) : CoroutineScope {

    suspend fun load(event: Event): Result<EventContext> = runSuspendCatching {
        val classesAsync = async { classService.list(event).getOrThrow() }
        val participantsAsync = async { participantService.list(event).getOrThrow() }
        val runsAsync = async { runService.list(event).getOrThrow() }
        val extendedParametersAsync = async { eventExtendedParametersService.load(event).getOrThrow() }
        EventContext(
            classes = classesAsync.await(),
            participants = participantsAsync.await(),
            runs = runsAsync.await(),
            extendedParameters = extendedParametersAsync.await()
        )
    }

}