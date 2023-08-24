package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Event
import tech.coner.trailer.Run
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.model.RunModel

class RunSequenceStringFieldAdapter : StringFieldAdapter<Run> {
    override operator fun invoke(model: Run): String {
        return model.sequence.toString()
    }
}

class RunRerunStringFieldAdapter : StringFieldAdapter<Run> {
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
) : Adapter<RunModelAdapter.Input, RunModel> {
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
