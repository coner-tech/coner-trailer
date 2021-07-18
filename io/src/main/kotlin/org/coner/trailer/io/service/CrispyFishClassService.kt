package org.coner.trailer.io.service

import org.coner.trailer.Class
import org.coner.trailer.datasource.crispyfish.CrispyFishClassMapper
import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import java.io.File

class CrispyFishClassService(
    private val crispyFishRoot: File,
    private val mapper: CrispyFishClassMapper
) {

    fun loadAllClasses(
        crispyFishClassDefinitionFile: String
    ): List<Class> {
        val allClassDefinitions = ClassDefinitionFile(
            file = crispyFishRoot.resolve(crispyFishClassDefinitionFile)
        ).mapper().all()
        return allClassDefinitions.mapIndexed(mapper::toCore)
    }

    fun loadAllByAbbreviation(
        crispyFishClassDefinitionFile: String
    ): Map<String, Class> = loadAllClasses(crispyFishClassDefinitionFile)
        .associateBy { it.abbreviation }

}
