package org.coner.trailer.datasource.crispyfish

import org.coner.trailer.Class
import org.coner.trailer.Classing
import tech.coner.crispyfish.model.Registration

class CrispyFishClassingMapper {

    fun toCore(allClassesByAbbreviation: Map<String, Class>, cfRegistration: Registration): Classing? {
        return Classing(
            group = cfRegistration.category?.abbreviation?.let { allClassesByAbbreviation[it] },
            handicap = cfRegistration.handicap?.abbreviation?.let { allClassesByAbbreviation[it] }
                ?: return null
        )
    }
}