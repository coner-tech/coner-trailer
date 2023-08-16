package tech.coner.trailer.io.service

import java.nio.file.Path
import kotlin.coroutines.CoroutineContext
import tech.coner.crispyfish.CrispyFishClassDefinitions
import tech.coner.crispyfish.CrispyFishClassDefinitionsFactory
import tech.coner.crispyfish.CrispyFishEventFactory
import tech.coner.crispyfish.model.EventDay
import tech.coner.trailer.Event
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.crispyfish.repository.CrispyFishStagingLogRepository
import tech.coner.trailer.io.constraint.CrispyFishLoadConstraints
import tech.coner.trailer.io.util.Cache

class CrispyFishEventMappingContextService(
    coroutineContext: CoroutineContext,
    private val cache: Cache<CrispyFishEventMappingContext.Key, CrispyFishEventMappingContext>,
    private val crispyFishDatabase: Path,
    private val loadConstraints: CrispyFishLoadConstraints,
    private val crispyFishClassDefinitionsFactory: CrispyFishClassDefinitionsFactory
    private val crispyFishEventFactory: CrispyFishEventFactory
) : CoroutineContext by coroutineContext {

    suspend fun load(
        event: Event,
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
    ): CrispyFishEventMappingContext {
        val key = CrispyFishEventMappingContext.Key(
            eventControlFile = crispyFishDatabase.resolve(eventCrispyFishMetadata.eventControlFile),
            classDefinitionFile = crispyFishDatabase.resolve(eventCrispyFishMetadata.classDefinitionFile)
        )
        return when (event.lifecycle) {
            Event.Lifecycle.ACTIVE -> performLoad(event, key) // when event is active, don't use cache
            else -> cache.getOrCreate(key) { performLoad(event, key) }
        }
    }

    private fun performLoad(event: Event, key: CrispyFishEventMappingContext.Key): CrispyFishEventMappingContext {
        loadConstraints.assess(key)
        val crispyFishClassDefinitions = crispyFishClassDefinitionsFactory(key.classDefinitionFile)
        val crispyFishEvent = crispyFishEventFactory(key.eventControlFile)
        val allClassDefinitions = crispyFishClassDefinitions.queryAllClassDefinitions()
        val allRegistrations = crispyFishEvent.queryAllRegistrations(allClassDefinitions)
        val registrationsBySignage = crispyFishEvent.queryAllRegistrationsBySignage(allClassDefinitions, allRegistrations)
        val allRuns = when (event.lifecycle) {
            Event.Lifecycle.ACTIVE -> stagingLogRepository.getDistinctRuns(eventControlFile, EventDay.ONE)
            else -> eventControlFile.queryStagingRuns(EventDay.ONE)
        }
        return CrispyFishEventMappingContext(
            allClassDefinitions = classDefinitionFile.mapper().all(),
            allRegistrations = allRegistrations,
            allRuns = allRuns.map {
                val registration = it.stagingRegistration?.signage?.let { stagingSignage ->
                    val registration = registrationsBySignage[stagingSignage]
                    registration
                }
                registration to it.run
            },
            staging = allRuns,
            runCount = allRegistrations.maxOf { it.runs.size }
        )
    }
}