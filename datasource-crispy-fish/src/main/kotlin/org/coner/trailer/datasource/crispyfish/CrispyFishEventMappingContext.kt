package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.ClassDefinition

class CrispyFishEventMappingContext(allClassDefinitions: List<ClassDefinition>) {
    val classDefinitionAbbreviationToSort: Map<String, Int> = allClassDefinitions
        .mapIndexed { index, classDefinition ->
            classDefinition.abbreviation to index
        }
        .toMap()
}