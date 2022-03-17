package tech.coner.trailer.io.service

import tech.coner.trailer.Class
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassParentMapper
import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import java.io.File
import java.nio.file.Path

class CrispyFishClassService(
    private val crispyFishRoot: File,
    private val classMapper: CrispyFishClassMapper,
    private val classParentMapper: CrispyFishClassParentMapper
) {

    fun loadAllClasses(
        crispyFishClassDefinitionFile: Path
    ): List<Class> {
        val allClassDefinitions = ClassDefinitionFile(
            file = crispyFishRoot.resolve(crispyFishClassDefinitionFile.toFile())
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
        crispyFishClassDefinitionFile: Path
    ): Map<String, Class> = loadAllClasses(crispyFishClassDefinitionFile)
        .associateBy { it.abbreviation }

}
