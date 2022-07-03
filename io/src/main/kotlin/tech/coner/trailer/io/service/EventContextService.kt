package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.EventContext

class EventContextService(
    private val classService: ClassService,
    private val participantService: ParticipantService,
    private val runService: RunService,
    private val eventExtendedParametersService: EventExtendedParametersService
) {

    fun load(event: Event): Result<EventContext> = runCatching {
        EventContext(
            classes = classService.list(event).getOrThrow(),
            participants = participantService.list(event).getOrThrow(),
            runs = runService.list(event).getOrThrow(),
            extendedParameters = eventExtendedParametersService.load(event).getOrThrow()
        )
    }
}