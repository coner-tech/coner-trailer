package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Car
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter

class CarColorStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Car?> {
    override operator fun invoke(model: Car?): String {
        return model?.color ?: ""
    }
}

class CarModelStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Car?> {
    override operator fun invoke(model: Car?): String {
        return model?.model ?: ""
    }
}
