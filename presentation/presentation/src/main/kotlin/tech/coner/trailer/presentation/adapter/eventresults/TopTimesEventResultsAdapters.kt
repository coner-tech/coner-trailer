package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.adapter.ClassParentModelAdapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.model.eventresults.TopTimesEventResultsCollectionModel
import tech.coner.trailer.presentation.model.eventresults.TopTimesEventResultsModel

class TopTimesEventResultsModelAdapter(
    override val participantResultAdapter: ParticipantResultModelAdapter,
    override val eventNameAdapter: EventNameStringFieldAdapter,
    override val eventDateAdapter: EventDateStringFieldAdapter,
    override val eventResultsTypeKeyAdapter: EventResultsTypeKeyStringFieldAdapter,
    override val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter,
    override val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter,
    val topTimesEventResultsCollectionModelAdapter: TopTimesEventResultsCollectionModelAdapter
) : EventResultsModelAdapter<TopTimesEventResults, TopTimesEventResultsModel>() {
    override fun invoke(model: TopTimesEventResults): TopTimesEventResultsModel {
        return TopTimesEventResultsModel(
            eventResults = model,
            adapter = this
        )
    }
}

class TopTimesEventResultsCollectionModelAdapter(
    private val classParentModelAdapter: ClassParentModelAdapter,
    private val participantResultModelAdapter: ParticipantResultModelAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<TopTimesEventResults, TopTimesEventResultsCollectionModel> {
    override fun invoke(model: TopTimesEventResults): TopTimesEventResultsCollectionModel {
        return TopTimesEventResultsCollectionModel(
            items = model.topTimes.map { (classParent, participantResult) ->
                TopTimesEventResultsCollectionModel.Item(
                    classParent = classParentModelAdapter(classParent),
                    participantResult = participantResultModelAdapter(participantResult, model)
                )
            }
        )
    }

}