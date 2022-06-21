package tech.coner.trailer.io.service

import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.model.EventDay
import tech.coner.trailer.Event
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.io.constraint.CrispyFishLoadConstraints
import java.nio.file.Path

class CrispyFishEventMappingContextService(
    private val crispyFishDatabase: Path,
    private val loadConstraints: CrispyFishLoadConstraints
) {

    fun load(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
    ): CrispyFishEventMappingContext {
        val key = CrispyFishEventMappingContext.Key(
            eventControlFile = crispyFishDatabase.resolve(eventCrispyFishMetadata.eventControlFile),
            classDefinitionFile = crispyFishDatabase.resolve(eventCrispyFishMetadata.classDefinitionFile)
        )
        loadConstraints.assess(key)
        val classDefinitionFile = ClassDefinitionFile(key.classDefinitionFile.toFile())
        val eventControlFile = EventControlFile(
            file = key.eventControlFile.toFile(),
            classDefinitionFile = classDefinitionFile,
            isTwoDayEvent = false,
            conePenalty = 2
        )
        val allRegistrations = eventControlFile.queryRegistrations()
        val registrationsBySignage = allRegistrations.associateBy { it.signage }
        val allRuns = eventControlFile.queryStagingRuns(EventDay.ONE)
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