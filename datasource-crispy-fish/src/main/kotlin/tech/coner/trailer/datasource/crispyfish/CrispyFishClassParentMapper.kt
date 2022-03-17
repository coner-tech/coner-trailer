package tech.coner.trailer.datasource.crispyfish

import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.trailer.Class

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