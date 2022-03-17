package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.trailer.Class
import java.math.BigDecimal

class CrispyFishClassMapper {

    fun toCore(
        allClassParentsByName: Map<String, Class.Parent>,
        index: Int,
        cfClassDefinition: ClassDefinition
    ) = Class(
        abbreviation = cfClassDefinition.abbreviation,
        name = cfClassDefinition.name,
        sort = index,
        paxed = cfClassDefinition.paxed,
        paxFactor = when {
            cfClassDefinition.paxFactor > BigDecimal.ZERO -> cfClassDefinition.paxFactor
            else -> null
        },
        parent = allClassParentsByName[cfClassDefinition.groupName]
    )
}