package org.coner.trailer.io.service

import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import org.coner.trailer.Event
import org.coner.trailer.Grouping
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
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
        crispyFish: Event.CrispyFishMetadata,
        abbreviation: String
    ) : Grouping.Singular {
        return findSingular(
            allSingulars = loadAllSingulars(crispyFish),
            abbreviation = abbreviation
        )
    }

    fun findPaired(
        allSingulars: List<Grouping.Singular>,
        abbreviations: Pair<String, String>
    ) : Grouping.Paired {
        val first = allSingulars.single { it.abbreviation == abbreviations.first }
        val second = allSingulars.single { it.abbreviation == abbreviations.second }
        return Grouping.Paired(pair = first to second)
    }

    fun findPaired(
        crispyFish: Event.CrispyFishMetadata,
        abbreviations: Pair<String, String>,
    ) : Grouping.Paired {
        return findPaired(
            allSingulars = loadAllSingulars(crispyFish),
            abbreviations = abbreviations
        )
    }

    fun loadAllSingulars(crispyFish: Event.CrispyFishMetadata): List<Grouping.Singular> {
        val allClassDefinitions = ClassDefinitionFile(
            file = crispyFishRoot.resolve(crispyFish.classDefinitionFile)
        ).mapper().all()
        val context = CrispyFishEventMappingContext(
            allClassDefinitions = allClassDefinitions,
            allRegistrations = emptyList()
        )
        return allClassDefinitions.map {
            mapper.toCoreSingular(context = context, classDefinition = it)
        }
    }

    fun loadAllSingularGroupingsByAbbreviation(crispyFish: Event.CrispyFishMetadata): Map<String, Grouping.Singular> {
        return loadAllSingulars(crispyFish).associateBy { it.abbreviation }
    }

}