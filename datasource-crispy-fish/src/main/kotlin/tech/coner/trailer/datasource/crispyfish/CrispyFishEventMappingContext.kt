package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.StagingRun
import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import tech.coner.trailer.datasource.crispyfish.util.syntheticSignageKey
import java.nio.file.Path

class CrispyFishEventMappingContext(
    val allClassDefinitions: List<ClassDefinition>,
    val allRegistrations: List<Registration>,
    val allRuns: List<Pair<Registration?, Run?>>,
    val staging: List<StagingRun>,
    val runCount: Int
) {
    val classDefinitionAbbreviationToSort: Map<String, Int> = allClassDefinitions
        .mapIndexed { index, classDefinition ->
            classDefinition.abbreviation to index
        }
        .toMap()

    val runsByRegistration: Map<Registration, List<Pair<Int, Run>>> = allRuns
        .mapIndexedNotNull { index, pair ->
            val registration = pair.first
            val run = pair.second
            if (registration != null && run != null) {
                registration to (index to run)
            } else {
                null
            }
        }
        .groupBy(
            keySelector = { (registration, _) -> registration.syntheticSignageKey() to registration },
            valueTransform = { (_, runPair) -> runPair }
        )
        .map { (key, value) -> key.second to value }
        .toMap()

    data class Key(
        val eventControlFile: Path,
        val classDefinitionFile: Path
    )
}