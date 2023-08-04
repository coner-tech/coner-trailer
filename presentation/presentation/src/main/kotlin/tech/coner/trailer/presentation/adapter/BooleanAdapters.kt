package tech.coner.trailer.presentation.adapter

class YesNoStringFieldAdapter : StringFieldAdapter<Boolean> {
    override fun invoke(model: Boolean): String {
        return when (model) {
            true -> "Yes"
            else -> "No"
        }
    }
}