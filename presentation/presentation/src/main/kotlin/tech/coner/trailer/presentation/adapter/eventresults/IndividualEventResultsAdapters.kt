package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ParticipantModelAdapter
import tech.coner.trailer.presentation.model.eventresults.EventResultsTypesCollectionModel
import tech.coner.trailer.presentation.model.eventresults.IndividualEventResultsCollectionModel
import tech.coner.trailer.presentation.model.eventresults.IndividualEventResultsModel

class IndividualEventResultsModelAdapter(
    override val participantResultAdapter: ParticipantResultModelAdapter,
    override val eventNameAdapter: EventNameStringFieldAdapter,
    override val eventDateAdapter: EventDateStringFieldAdapter,
    override val eventResultsTypeKeyAdapter: EventResultsTypeKeyStringFieldAdapter,
    override val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter,
    override val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter,
    val innerEventResultsTypes: IndividualInnerEventResultsTypesCollectionModelAdapter,
    val resultsByIndividualAdapter: IndividualEventResultsCollectionModelAdapter
) : EventResultsModelAdapter<IndividualEventResults, IndividualEventResultsModel>() {
    override fun invoke(model: IndividualEventResults): IndividualEventResultsModel {
        return IndividualEventResultsModel(
            eventResults = model,
            adapter = this,
            innerEventResultsTypes = innerEventResultsTypes(model),
            resultsByIndividual = resultsByIndividualAdapter(model)
        )
    }
}

class IndividualInnerEventResultsTypesCollectionModelAdapter(
    private val eventResultsTypeAdapter: EventResultsTypeModelAdapter
) : Adapter<IndividualEventResults, EventResultsTypesCollectionModel> {
    override fun invoke(model: IndividualEventResults): EventResultsTypesCollectionModel {
        return EventResultsTypesCollectionModel(
            items = model.innerEventResultsTypes.map { eventResultsTypeAdapter(it) }
        )
    }

}

class IndividualEventResultsCollectionModelAdapter(
    private val participantAdapter: ParticipantModelAdapter,
    private val eventResultsTypeAdapter: EventResultsTypeModelAdapter,
    private val participantResultAdapter: ParticipantResultModelAdapter
) : Adapter<IndividualEventResults, IndividualEventResultsCollectionModel> {
    override fun invoke(model: IndividualEventResults): IndividualEventResultsCollectionModel {
        return IndividualEventResultsCollectionModel(
            items = model.resultsByIndividual
                .map { (participant, participantResultByTypeMap) ->
                    IndividualEventResultsCollectionModel.Item(
                        participant = participantAdapter(participant, model.eventContext.event),
                        typedParticipantResults = participantResultByTypeMap
                            .map { (eventResultsType, participantResult) ->
                                IndividualEventResultsCollectionModel.TypedParticipantResultModel(
                                    eventResultsType = eventResultsTypeAdapter(eventResultsType),
                                    participantResult = participantResult?.let{ participantResultAdapter(it, model) }
                                )
                            }
                    )
                }
        )
    }

}