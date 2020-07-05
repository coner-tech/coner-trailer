package org.coner.trailer.datasource.crispyfish

import org.coner.crispyfish.model.ClassDefinition
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Grouping

object GroupingMapper {

    fun map(classDefinition: ClassDefinition): Grouping {
        return Grouping.Singular(
                abbreviation = classDefinition.abbreviation,
                name = classDefinition.name
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