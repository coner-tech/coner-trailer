package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.adapter.EventRunLatestModelAdapter
import tech.coner.trailer.presentation.constraint.EventRunLatestConstraints
import tech.coner.trailer.presentation.model.util.ItemModel
import tech.coner.trailer.presentation.state.EventRunLatestState

class EventRunLatestModel(
    override val original: EventRunLatestState,
    override val constraints: EventRunLatestConstraints,
    override val adapter: EventRunLatestModelAdapter
) : ItemModel<EventRunLatestState, EventRunLatestConstraints, EventRunLatestModelAdapter>() {

    var count
        get() = itemValue.count
        set(value) { updateItem { it.copy(count = value) } }

    val latestRuns: RunCollectionModel
        get() = itemValue
            .let { state -> adapter.runCollectionModelAdapter(state) }
}
