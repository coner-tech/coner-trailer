package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Time

class TimeStringFieldAdapter : StringFieldAdapter<Time> {
    override operator fun invoke(model: Time): String {
        return model.value.toString()
    }
}

class NullableTimeStringFieldAdapter(
    private val timeStringFieldAdapter: TimeStringFieldAdapter
) : StringFieldAdapter<Time?> {
    override operator fun invoke(model: Time?): String {
        return model?.let(timeStringFieldAdapter::invoke) ?: ""
    }
}
