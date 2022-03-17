package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Class
import tech.coner.trailer.Classing

class CrispyFishClassingMapper {

    fun toCore(allClassesByAbbreviation: Map<String, Class>, cfRegistration: Registration): Classing? {
        return Classing(
            group = cfRegistration.category?.abbreviation?.let { allClassesByAbbreviation[it] },
            handicap = cfRegistration.handicap?.abbreviation?.let { allClassesByAbbreviation[it] }
                ?: return null
        )
    }
}