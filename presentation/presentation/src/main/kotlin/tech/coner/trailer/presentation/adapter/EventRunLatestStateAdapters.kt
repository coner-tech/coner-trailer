package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.presentation.constraint.EventRunLatestConstraints
import tech.coner.trailer.presentation.model.EventRunLatestModel
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.state.EventRunLatestState

class EventRunLatestModelAdapter(
    private val constraints: EventRunLatestConstraints,
    val runCollectionModelAdapter: Adapter<EventRunLatestState, RunCollectionModel>,
) : Adapter<EventRunLatestState, EventRunLatestModel> {
    override fun invoke(model: EventRunLatestState): EventRunLatestModel {
        return EventRunLatestModel(model, constraints, this)
    }
}

class EventRunLatestCollectionModelAdapter(
    private val runAdapter: RunModelAdapter
) : Adapter<EventRunLatestState, RunCollectionModel> {
    private val nullEarlyReturn = RunCollectionModel(emptyList())

    override fun invoke(model: EventRunLatestState): RunCollectionModel {
        val eventContext = model.eventContext ?: return nullEarlyReturn
        val latestRuns = model.latestRuns ?: return nullEarlyReturn
        return RunCollectionModel(
            latestRuns.map { runAdapter(it, eventContext.event) }
        )
    }

}