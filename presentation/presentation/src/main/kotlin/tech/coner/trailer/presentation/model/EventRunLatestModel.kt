package tech.coner.trailer.presentation.model

import tech.coner.trailer.presentation.adapter.EventRunLatestModelAdapter
import tech.coner.trailer.presentation.constraint.EventRunLatestConstraints
import tech.coner.trailer.presentation.library.model.BaseItemModel
import tech.coner.trailer.presentation.state.EventRunLatestState

class EventRunLatestModel(
    override val item: EventRunLatestState,
    override val constraints: EventRunLatestConstraints,
    private val adapter: EventRunLatestModelAdapter
) : BaseItemModel<EventRunLatestState, EventRunLatestConstraints>() {

    var count
        get() = pendingItem.count
        set(value) { mutatePendingItem { it.copy(count = value) } }

    val latestRuns: RunCollectionModel
        get() = pendingItem
            .let { state -> adapter.runCollectionModelAdapter(state) }
}
