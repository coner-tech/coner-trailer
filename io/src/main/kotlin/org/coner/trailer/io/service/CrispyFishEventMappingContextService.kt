package org.coner.trailer.io.service

import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.util.syntheticSignageKey
import org.coner.trailer.io.constraint.CrispyFishLoadConstraints
import tech.coner.crispyfish.model.EventDay
import tech.coner.crispyfish.model.Registration
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
        val registrationsBySyntheticSignage = allRegistrations.associateBy { it.syntheticSignageKey() }
        val allRuns = eventControlFile.queryStagingRuns(EventDay.ONE)
        return CrispyFishEventMappingContext(
            allClassDefinitions = classDefinitionFile.mapper().all(),
            allRegistrations = allRegistrations,
            allRuns = allRuns.map { (stagingLineRegistration, run) ->
                val registration = if (stagingLineRegistration != null) {
                    val stagingLineRegistrationSyntheticSignage = "${stagingLineRegistration.classing} ${stagingLineRegistration.number}"
                    val registration = registrationsBySyntheticSignage[stagingLineRegistrationSyntheticSignage]
                    registration
                } else {
                    null
                }
                registration to run
            },
            runCount = allRegistrations.maxOf { it.runs.size }
        )
    }
}