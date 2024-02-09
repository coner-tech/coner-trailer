package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.Run
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel

class RunSequenceStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Run> {
    override operator fun invoke(model: Run): String {
        return model.sequence.toString()
    }
}

class RunRerunStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Run> {
    override operator fun invoke(model: Run): String {
        return if (model.rerun) Strings.abbreviationRerun else ""
    }
}

class RunModelAdapter(
    val sequenceStringFieldAdapter: RunSequenceStringFieldAdapter,
    val signageStringFieldAdapter: SignageStringFieldAdapter,
    val carModelStringFieldAdapter: CarModelStringFieldAdapter,
    val carColorStringFieldAdapter: CarColorStringFieldAdapter,
    val nullableParticipantFullNameStringFieldAdapter: NullableParticipantFullNameStringFieldAdapter,
    val nullableTimeStringFieldAdapter: NullableTimeStringFieldAdapter,
    val penaltyCollectionStringFieldAdapter: PenaltyCollectionStringFieldAdapter,
    val rerunStringFieldAdapter: RunRerunStringFieldAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<RunModelAdapter.Input, RunModel> {
    override fun invoke(model: Input): RunModel {
        return RunModel(
            event = model.event,
            run = model.run,
            adapter = this
        )
    }

    operator fun invoke(run: Run, event: Event) = invoke(Input(run, event))

    data class Input(
        val run: Run,
        val event: Event
    )
}

class EventContextRunCollectionModelAdapter(
    private val runAdapter: RunModelAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<EventContext, RunCollectionModel> {
    override fun invoke(model: EventContext): RunCollectionModel {
        return RunCollectionModel(
            items = model.runs.map { runAdapter(it, model.event) }
        )
    }
}

class ArbitraryRunsCollectionModelAdapter(
    private val runAdapter: RunModelAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<Pair<Event, Collection<Run>>, RunCollectionModel> {

    override fun invoke(model: Pair<Event, Collection<Run>>): RunCollectionModel {
        return RunCollectionModel(
            items = model.second.map { runAdapter(it, model.first) }
        )
    }
}
