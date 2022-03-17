package tech.coner.trailer.datasource.crispyfish.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.eventresults.OverallEventResults

interface CrispyFishOverallEventResultsFactory {

    fun factory(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        allClassesByAbbreviation: Map<String, Class>,
        context: CrispyFishEventMappingContext
    ) : OverallEventResults

}