package org.coner.trailer.io.service

import org.coner.trailer.Class
import org.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishClassParentMapper
import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import java.io.File

class CrispyFishClassService(
    private val crispyFishRoot: File,
    private val classMapper: CrispyFishClassMapper,
    private val classParentMapper: CrispyFishClassParentMapper
) {

    fun loadAllClasses(
        crispyFishClassDefinitionFile: String
    ): List<Class> {
        val allClassDefinitions = ClassDefinitionFile(
            file = crispyFishRoot.resolve(crispyFishClassDefinitionFile)
        ).mapper().all()
        val allClassParentsByName = classParentMapper
            .toCores(allClassDefinitions)
            .associateBy { it.name }
        return allClassDefinitions.mapIndexed { index, cfClassDefinition ->
            classMapper.toCore(
                allClassParentsByName = allClassParentsByName,
                index = index,
                cfClassDefinition = cfClassDefinition
            )
        }
    }

    fun loadAllByAbbreviation(
        crispyFishClassDefinitionFile: String
    ): Map<String, Class> = loadAllClasses(crispyFishClassDefinitionFile)
        .associateBy { it.abbreviation }

}
