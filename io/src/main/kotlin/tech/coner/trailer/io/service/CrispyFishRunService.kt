package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Signage
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper

class CrispyFishRunService(
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService,
    private val crispyFishClassService: CrispyFishClassService,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val crispyFishRunMapper: CrispyFishRunMapper
) {
    fun list(event: Event): Result<List<Run>> = try {
        val eventCrispyFish = event.requireCrispyFish()
        val context = crispyFishEventMappingContextService.load(eventCrispyFish)
        val allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(eventCrispyFish.classDefinitionFile)
        val runs = context.staging
            .mapIndexed { index, stagingRun ->
                crispyFishRunMapper.toCore(
                    cfRun = stagingRun.run,
                    cfRunIndex = index,
                    participant = crispyFishParticipantMapper.toCore(
                        allClassesByAbbreviation = allClassesByAbbreviation,
                        peopleMap = eventCrispyFish.peopleMap,
                        stagingRun = stagingRun
                    )
                )
            }
        Result.success(runs)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}