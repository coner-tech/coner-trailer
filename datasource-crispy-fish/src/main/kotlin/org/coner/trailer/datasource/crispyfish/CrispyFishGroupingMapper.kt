package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Grouping

class CrispyFishGroupingMapper(
        classDefinitions: List<ClassDefinition>
) {

    private val classDefinitionAbbreviationToSort: Map<String, Int> = classDefinitions
            .mapIndexed { index, classDefinition ->
                classDefinition.abbreviation to index
            }
            .toMap()

    fun map(classDefinition: ClassDefinition): Grouping.Singular {
        return Grouping.Singular(
                abbreviation = classDefinition.abbreviation,
                name = classDefinition.name,
                sort = classDefinitionAbbreviationToSort[classDefinition.abbreviation]
                        ?: throw IllegalArgumentException("No sort mapping for ClassDefinition: $classDefinition")
        )
    }

    fun map(fromRegistration: Registration): Grouping {
        val category = fromRegistration.category
        return if (category != null) {
            Grouping.Paired(map(category) to map(fromRegistration.handicap))
        } else {
            map(fromRegistration.handicap)
        }
    }
}