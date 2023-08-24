package tech.coner.trailer.io.service

import tech.coner.crispyfish.CrispyFishClassDefinitionsFactory
import tech.coner.trailer.Class
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassParentMapper
import java.nio.file.Path

class CrispyFishClassService(
    private val crispyFishRoot: Path,
    private val crispyFishClassDefinitionsFactory: CrispyFishClassDefinitionsFactory,
    private val classMapper: CrispyFishClassMapper,
    private val classParentMapper: CrispyFishClassParentMapper
) {

    fun loadAllClasses(
        crispyFishClassDefinitionFile: Path
    ): List<Class> {
        val allClassDefinitions = crispyFishClassDefinitionsFactory(crispyFishRoot.resolve(crispyFishClassDefinitionFile))
            .queryAllClassDefinitions()
        val allClassParentsByName = classParentMapper
            .toCores(allClassDefinitions.combined)
            .associateBy { it.name }
        return allClassDefinitions.combined.mapIndexed { index, cfClassDefinition ->
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
