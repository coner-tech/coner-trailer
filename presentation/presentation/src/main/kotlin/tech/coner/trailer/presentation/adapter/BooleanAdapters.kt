package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter

class YesNoStringFieldAdapter : StringFieldAdapter<Boolean> {
    override fun invoke(model: Boolean): String {
        return when (model) {
            true -> "Yes"
            else -> "No"
        }
    }
}