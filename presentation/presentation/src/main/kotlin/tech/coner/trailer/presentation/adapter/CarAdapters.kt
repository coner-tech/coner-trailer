package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Car

class CarColorStringFieldAdapter : StringFieldAdapter<Car?> {
    override operator fun invoke(model: Car?): String {
        return model?.color ?: ""
    }
}

class CarModelStringFieldAdapter : StringFieldAdapter<Car?> {
    override operator fun invoke(model: Car?): String {
        return model?.model ?: ""
    }
}
