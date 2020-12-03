package org.coner.trailer.io.service

import org.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.trailer.Grouping
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import java.io.File

class CrispyFishGroupingService(
    private val crispyFishRoot: File,
    private val mapper: CrispyFishGroupingMapper
) {

    fun findSingular(
        crispyFishClassDefinitionFile: String,
        abbreviation: String,
        allSingulars: List<Grouping.Singular> = loadAllSingulars(crispyFishClassDefinitionFile)
    ) : Grouping.Singular {
        return allSingulars.single { it.abbreviation == abbreviation }
    }

    fun findPaired(
        crispyFishClassDefinitionFile: String,
        abbreviations: Pair<String, String>,
        allSingulars: List<Grouping.Singular> = loadAllSingulars(crispyFishClassDefinitionFile)
    ) : Grouping.Paired {
        val first = allSingulars.single { it.abbreviation == abbreviations.first }
        val second = allSingulars.single { it.abbreviation == abbreviations.second }
        return Grouping.Paired(pair = first to second)
    }

    fun loadAllSingulars(crispyFishClassDefinitionFile: String): List<Grouping.Singular> {
        val allClassDefinitions = ClassDefinitionFile(
            file = crispyFishRoot.resolve(crispyFishClassDefinitionFile)
        ).mapper().all()
        return allClassDefinitions.map(mapper::map)
    }

}