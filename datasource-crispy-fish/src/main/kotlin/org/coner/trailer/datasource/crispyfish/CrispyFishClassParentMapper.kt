package org.coner.trailer.datasource.crispyfish

import org.coner.trailer.Class
import tech.coner.crispyfish.model.ClassDefinition

class CrispyFishClassParentMapper {

    fun toCores(allClassDefinitions: List<ClassDefinition>): List<Class.Parent> {
        return allClassDefinitions
            .filter { it.groupName.isNotEmpty() }
            .distinctBy { it.groupName }
            .mapIndexed { index, classDefinition -> Class.Parent(
                name = classDefinition.groupName,
                sort = index
            ) }
    }
}