package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.presentation.adapter.eventresults.IndividualEventResultsModelAdapter

class IndividualEventResultsModel(
    override val eventResults: IndividualEventResults,
    override val adapter: IndividualEventResultsModelAdapter,
    val innerEventResultsTypes: EventResultsTypesCollectionModel,
    val resultsByIndividual: IndividualEventResultsCollectionModel
) : EventResultsModel<IndividualEventResults>()
