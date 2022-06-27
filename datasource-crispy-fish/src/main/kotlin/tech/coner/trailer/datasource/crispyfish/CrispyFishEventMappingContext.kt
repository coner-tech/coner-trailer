package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.model.StagingRun
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

    val runsByRegistration: Map<Registration, List<Pair<Int, StagingRun>>> = staging
        .mapIndexed { index, stagingRun ->
            stagingRun.registration to (index to stagingRun)
        }
        .groupBy(
            keySelector = { (registration, _) -> registration?.signage to registration },
            valueTransform = { (_, stagingRun) -> stagingRun }
        )
        .mapNotNull { (signageKey, stagingRuns) ->
            val registration = signageKey.second
            if (registration != null) {
                registration to stagingRuns
            } else {
                null
            }
        }
        .toMap()

    data class Key(
        val eventControlFile: Path,
        val classDefinitionFile: Path
    )
}