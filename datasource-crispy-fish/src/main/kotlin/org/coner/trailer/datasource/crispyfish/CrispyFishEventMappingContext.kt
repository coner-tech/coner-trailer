package org.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import java.nio.file.Path

class CrispyFishEventMappingContext(
    val allClassDefinitions: List<ClassDefinition>,
    val allRegistrations: List<Registration>,
    val allRuns: List<Pair<Registration?, Run?>>,
    val runCount: Int
) {
    val classDefinitionAbbreviationToSort: Map<String, Int> = allClassDefinitions
        .mapIndexed { index, classDefinition ->
            classDefinition.abbreviation to index
        }
        .toMap()

    val runsByRegistration: Map<Registration, List<Run>> = allRuns
        .mapNotNull { (registration, run) ->
            if (registration != null && run != null) {
                registration to run
            } else {
                null
            }
        }
        .groupBy(
            keySelector = { (registration, _) -> registration.syntheticSignageKey() to registration },
            valueTransform = { (_, run) -> run }
        )
        .map { (key, value) -> key.second to value }
        .toMap()

    data class Key(
        val eventControlFile: Path,
        val classDefinitionFile: Path
    )
}