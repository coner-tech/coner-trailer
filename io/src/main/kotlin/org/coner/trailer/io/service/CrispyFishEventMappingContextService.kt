package org.coner.trailer.io.service

import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.io.constraint.CrispyFishLoadConstraints
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class CrispyFishEventMappingContextService(
    private val crispyFishDatabase: Path,
    private val loadConstraints: CrispyFishLoadConstraints
) {

    fun load(
        eventControlFilePath: Path,
        classDefinitionFilePath: Path
    ): CrispyFishEventMappingContext {
        val key = CrispyFishEventMappingContext.Key(
            eventControlFile = crispyFishDatabase.resolve(eventControlFilePath),
            classDefinitionFile = crispyFishDatabase.resolve(classDefinitionFilePath)
        )
        loadConstraints.assess(key)
        val classDefinitionFile = ClassDefinitionFile(key.classDefinitionFile.toFile())
        val eventControlFile = EventControlFile(
            file = key.eventControlFile.toFile(),
            classDefinitionFile = classDefinitionFile,
            isTwoDayEvent = false,
            conePenalty = 2
        )
        return CrispyFishEventMappingContext(
            allClassDefinitions = classDefinitionFile.mapper().all(),
            allRegistrations = eventControlFile.queryRegistrations()
        )
    }
}