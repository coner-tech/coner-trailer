package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.EventContext
import tech.coner.trailer.presentation.model.RunCollectionModel

class RunCollectionModelAdapter(
    private val runAdapter: RunModelAdapter
) : Adapter<EventContext, RunCollectionModel> {
    override fun invoke(model: EventContext): RunCollectionModel {
        return RunCollectionModel(
            items = model.runs.map { runAdapter(it, model.event) }
        )
    }
}
