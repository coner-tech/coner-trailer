package tech.coner.trailer.io.service

import tech.coner.crispyfish.CrispyFishClassDefinitionsFactory
import tech.coner.crispyfish.CrispyFishEventFactory
import tech.coner.crispyfish.model.AllStagingLogRows
import tech.coner.crispyfish.model.EventDay
import tech.coner.crispyfish.model.StagingLogRow
import tech.coner.crispyfish.model.StagingRun
import tech.coner.trailer.Event
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.io.constraint.CrispyFishLoadConstraints
import tech.coner.trailer.io.util.Cache
import java.nio.file.Path
import kotlin.coroutines.CoroutineContext

class CrispyFishEventMappingContextService(
    coroutineContext: CoroutineContext,
    private val nonActiveCache: Cache<CrispyFishEventMappingContext.Key, CrispyFishEventMappingContext>,
    private val crispyFishDatabase: Path,
    private val loadConstraints: CrispyFishLoadConstraints,
    private val crispyFishClassDefinitionsFactory: CrispyFishClassDefinitionsFactory,
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
            Event.Lifecycle.ACTIVE -> performLoad(event, key) // no cache for active events
            else -> nonActiveCache.getOrCreate(key) { performLoad(event, key) }
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
            Event.Lifecycle.ACTIVE -> crispyFishEvent.queryAllStagingLogRows(EventDay.ONE, allClassDefinitions, registrationsBySignage).toStagingRuns()
            else -> crispyFishEvent.queryAllStagingRuns(EventDay.ONE, allClassDefinitions, registrationsBySignage).value
        }
        return CrispyFishEventMappingContext(
            allClassDefinitions = allClassDefinitions.combined,
            allRegistrations = allRegistrations.value,
            allRuns = allRuns.map {
                val registration = it.stagingRegistration?.signage?.let { stagingSignage ->
                    val registration = registrationsBySignage.value[stagingSignage]
                    registration
                }
                registration to it.run
            },
            staging = allRuns,
            runCount = allRegistrations.value.maxOf { it.runs.size }
        )
    }

    private fun AllStagingLogRows.toStagingRuns(): List<StagingRun> {
        return value
            .groupBy(StagingLogRow::stagingRunIndex)
            .map { it.value.last().stagingRun }
    }
}