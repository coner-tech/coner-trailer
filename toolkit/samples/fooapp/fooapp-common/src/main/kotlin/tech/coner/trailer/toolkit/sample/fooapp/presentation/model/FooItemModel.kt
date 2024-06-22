package tech.coner.trailer.toolkit.sample.fooapp.presentation.model

import tech.coner.trailer.toolkit.presentation.model.BaseItemModel
import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooValidator
import tech.coner.trailer.toolkit.sample.fooapp.presentation.adapter.FooEntityModelAdapter
import tech.coner.trailer.toolkit.sample.fooapp.presentation.validation.FooModelFeedback
import tech.coner.trailer.toolkit.validation.Validator

class FooItemModel(
    override val initialItem: FooModel,
    private val adapter: FooEntityModelAdapter = FooEntityModelAdapter()
) : BaseItemModel<FooModel, Unit, FooModelFeedback>() {

    override val validator: Validator<Unit, FooModel, FooModelFeedback> = Validator {
        input(
            otherTypeValidator = FooValidator(),
            mapContextFn = {},
            mapInputFn = { adapter.modelToEntityAdapter(it) },
            mapFeedbackObjectFn = { FooModelFeedback(it) }
        )
    }
    override val validatorContext = Unit
}

