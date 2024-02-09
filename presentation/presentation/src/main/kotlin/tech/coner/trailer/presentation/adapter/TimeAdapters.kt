package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Time
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter

class TimeStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Time> {
    override operator fun invoke(model: Time): String {
        return model.value.toString()
    }
}

class NullableTimeStringFieldAdapter(
    private val timeStringFieldAdapter: TimeStringFieldAdapter
) : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Time?> {
    override operator fun invoke(model: Time?): String {
        return model?.let(timeStringFieldAdapter::invoke) ?: ""
    }
}
