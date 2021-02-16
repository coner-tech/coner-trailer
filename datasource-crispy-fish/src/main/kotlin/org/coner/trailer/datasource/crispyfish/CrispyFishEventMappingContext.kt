package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.model.Registration
import java.nio.file.Path

class CrispyFishEventMappingContext(
    val allClassDefinitions: List<ClassDefinition>,
    val allRegistrations: List<Registration>
) {
    val classDefinitionAbbreviationToSort: Map<String, Int> = allClassDefinitions
        .mapIndexed { index, classDefinition ->
            classDefinition.abbreviation to index
        }
        .toMap()

    data class Key(
        val eventControlFile: Path,
        val classDefinitionFile: Path
    )
}