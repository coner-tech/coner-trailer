package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import kotlin.coroutines.CoroutineContext

class CrispyFishParticipantService(
    coroutineContext: CoroutineContext,
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService,
    private val crispyFishClassService: CrispyFishClassService,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper
) : CoroutineContext by coroutineContext {

    suspend fun list(event: Event): Result<List<Participant>> = try {
        val eventCrispyFish = event.requireCrispyFish()
        val context = crispyFishEventMappingContextService.load(event, eventCrispyFish)
        val allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(eventCrispyFish.classDefinitionFile)
        val participants = context.allRegistrations
            .map { registration ->
                crispyFishParticipantMapper.toCoreFromRegistration(
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    peopleMap = eventCrispyFish.peopleMap,
                    registration = registration
                )
            }
        Result.success(participants)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}
