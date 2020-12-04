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
        allSingulars: List<Grouping.Singular>,
        abbreviation: String
    ) : Grouping.Singular {
        return allSingulars.single { it.abbreviation == abbreviation }
    }

    fun findSingular(
        crispyFishClassDefinitionFile: String,
        abbreviation: String
    ) : Grouping.Singular {
        return findSingular(
            allSingulars = loadAllSingulars(crispyFishClassDefinitionFile),
            abbreviation = abbreviation
        )
    }

    fun findPaired(
        abbreviations: Pair<String, String>,
        allSingulars: List<Grouping.Singular>
    ) : Grouping.Paired {
        val first = allSingulars.single { it.abbreviation == abbreviations.first }
        val second = allSingulars.single { it.abbreviation == abbreviations.second }
        return Grouping.Paired(pair = first to second)
    }

    fun findPaired(
        crispyFishClassDefinitionFile: String,
        abbreviations: Pair<String, String>,
    ) : Grouping.Paired {
        return findPaired(
            allSingulars = loadAllSingulars(crispyFishClassDefinitionFile),
            abbreviations = abbreviations
        )
    }

    fun loadAllSingulars(crispyFishClassDefinitionFile: String): List<Grouping.Singular> {
        val allClassDefinitions = ClassDefinitionFile(
            file = crispyFishRoot.resolve(crispyFishClassDefinitionFile)
        ).mapper().all()
        return allClassDefinitions.map(mapper::map)
    }

}