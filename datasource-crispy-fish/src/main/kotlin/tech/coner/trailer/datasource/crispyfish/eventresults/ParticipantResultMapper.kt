package tech.coner.trailer.datasource.crispyfish.eventresults

import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.Time
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import tech.coner.trailer.eventresults.FinalScoreFactory
import tech.coner.trailer.eventresults.ParticipantResult

class ParticipantResultMapper(
    private val resultRunMapper: ResultRunMapper,
    private val finalScoreFactory: FinalScoreFactory,
    private val crispyFishClassingMapper: CrispyFishClassingMapper,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val crispyFishRunMapper: CrispyFishRunMapper
) {

    fun toCore(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext,
        allClassesByAbbreviation: Map<String, Class>,
        cfRegistration: Registration
    ): ParticipantResult? {
        val classing = crispyFishClassingMapper.toCore(
            allClassesByAbbreviation = allClassesByAbbreviation,
            cfRegistration = cfRegistration
        )
        val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
            classing = classing ?: return null,
            number = cfRegistration.number ?: return null,
            firstName = cfRegistration.firstName ?: return null,
            lastName = cfRegistration.lastName ?: return null
        )
        val participant = crispyFishParticipantMapper.toCore(
            allClassesByAbbreviation = allClassesByAbbreviation,
            fromRegistration = cfRegistration,
            withPerson = eventCrispyFishMetadata.peopleMap[peopleMapKey]
        )
        val participantCfRuns = context.runsByRegistration[cfRegistration] ?: return null
        val allRuns = participantCfRuns
            .map { cfRunPair ->
                crispyFishRunMapper.toCore(
                cfRun = cfRunPair.second,
                cfRunIndex = cfRunPair.first,
                participant = participant
            ) }
        val scoredRuns = resultRunMapper.toCores(
            context = context,
            participantCfRuns = participantCfRuns,
            participant = participant
        )
        return ParticipantResult(
            score = finalScoreFactory.score(scoredRuns) ?: return null,
            participant = participant,
            allRuns = allRuns,
            scoredRuns = scoredRuns,
            personalBestScoredRunIndex = finalScoreFactory.bestRun(scoredRuns)?.let { scoredRuns.indexOf(it) },
            // positions and diffs are calculated in toCoreRanked after sorting by score
            position = Int.MAX_VALUE,
            diffFirst = null,
            diffPrevious = null,
        )
    }

    fun toCoreRanked(sortedResults: List<ParticipantResult>, index: Int, result: ParticipantResult): ParticipantResult {
        return result.copy(
            position = index + 1,
            diffFirst = when {
                index == 0 -> null
                result.score.penalty?.diff == false -> null
                else -> Time(result.score.value - sortedResults[0].score.value)
            },
            diffPrevious = when {
                index == 0 -> null
                result.score.penalty?.diff == false -> null
                else -> Time(result.score.value - sortedResults[index - 1].score.value)
            }
        )
    }

}