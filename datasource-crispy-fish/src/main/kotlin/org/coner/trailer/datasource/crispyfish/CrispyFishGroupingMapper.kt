package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Grouping

class CrispyFishGroupingMapper {

    fun toCoreSingular(
        context: CrispyFishEventMappingContext,
        classDefinition: ClassDefinition): Grouping.Singular {
        return Grouping.Singular(
                abbreviation = classDefinition.abbreviation,
                name = classDefinition.name,
                sort = context.classDefinitionAbbreviationToSort[classDefinition.abbreviation]
                        ?: throw IllegalArgumentException("No sort mapping for ClassDefinition: $classDefinition")
        )
    }

    fun toCore(
        context: CrispyFishEventMappingContext,
        fromRegistration: Registration
    ): Grouping {
        val category = fromRegistration.category
        return if (category != null) {
            val first = toCoreSingular(context, category)
            val second = toCoreSingular(context, fromRegistration.handicap)
            Grouping.Paired(first to second)
        } else {
            toCoreSingular(context, fromRegistration.handicap)
        }
    }

}