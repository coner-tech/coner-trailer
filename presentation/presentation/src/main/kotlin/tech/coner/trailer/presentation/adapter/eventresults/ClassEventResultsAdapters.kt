package tech.coner.trailer.presentation.adapter.eventresults

import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.ClassModelAdapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.model.eventresults.ClassEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.ClassParticipantResultsCollectionModel

class ClassEventResultsModelAdapter(
    override val participantResultAdapter: ParticipantResultModelAdapter,
    override val eventNameAdapter: EventNameStringFieldAdapter,
    override val eventDateAdapter: EventDateStringFieldAdapter,
    override val eventResultsTypeTitleAdapter: EventResultsTypeTitleStringFieldAdapter,
    override val eventResultsTypeScoreColumnHeadingAdapter: EventResultsTypeScoreColumnHeadingStringFieldAdapter,
    val classParticipantResultsAdapter: ClassParticipantResultsCollectionModelAdapter
) : EventResultsModelAdapter<ClassEventResults, ClassEventResultsModel>() {
    override fun invoke(model: ClassEventResults): ClassEventResultsModel {
        return ClassEventResultsModel(
            eventResults = model,
            adapter = this,
            classEventResults = classParticipantResultsAdapter(model)
        )
    }

}

class ClassParticipantResultsCollectionModelAdapter(
    private val classModelAdapter: ClassModelAdapter,
    private val participantResultModelAdapter: ParticipantResultModelAdapter,
) : Adapter<ClassEventResults, ClassParticipantResultsCollectionModel> {
    override fun invoke(model: ClassEventResults): ClassParticipantResultsCollectionModel {
        return ClassParticipantResultsCollectionModel(
            items = model.groupParticipantResults
                .map { (clazz, participantResults) ->
                    ClassParticipantResultsCollectionModel.Item(
                        clazz = classModelAdapter(clazz),
                        participantResults = participantResults.map { participantResultModelAdapter(it, model) }
                    )
                },
            eventResults = model,
            adapter = this
        )
    }
}
