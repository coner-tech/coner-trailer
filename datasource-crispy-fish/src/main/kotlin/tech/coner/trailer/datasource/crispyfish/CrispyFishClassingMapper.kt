package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.StagingRegistration
import tech.coner.crispyfish.model.StagingRun
import tech.coner.trailer.Class
import tech.coner.trailer.Classing

class CrispyFishClassingMapper {

    fun toCore(allClassesByAbbreviation: Map<String, Class>, cfRegistration: Registration): Classing? {
        return Classing(
            group = cfRegistration.signage.classing?.category?.abbreviation?.let { allClassesByAbbreviation[it] },
            handicap = cfRegistration.signage.classing?.handicap?.abbreviation?.let { allClassesByAbbreviation[it] }
                ?: return null
        )
    }

    fun toCore(allClassesByAbbreviation: Map<String, Class>, cfStagingRun: StagingRun): Classing? {
        return toCore(
            allClassesByAbbreviation = allClassesByAbbreviation,
            cfStagingRegistration = cfStagingRun.stagingRegistration ?: return null
        )
    }

    fun toCore(allClassesByAbbreviation: Map<String, Class>, cfStagingRegistration: StagingRegistration): Classing? {
        return Classing(
            group = cfStagingRegistration.signage?.classing?.category?.abbreviation?.let { allClassesByAbbreviation[it] },
            handicap = cfStagingRegistration.signage?.classing?.handicap?.abbreviation?.let { allClassesByAbbreviation[it] }
                ?: return null
        )
    }
}