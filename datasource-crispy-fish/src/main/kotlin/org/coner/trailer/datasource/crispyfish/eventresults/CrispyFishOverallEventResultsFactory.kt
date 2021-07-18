package org.coner.trailer.datasource.crispyfish.eventresults

import org.coner.trailer.Class
import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.OverallEventResults

interface CrispyFishOverallEventResultsFactory {

    fun factory(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        allClassesByAbbreviation: Map<String, Class>,
        context: CrispyFishEventMappingContext
    ) : OverallEventResults

}