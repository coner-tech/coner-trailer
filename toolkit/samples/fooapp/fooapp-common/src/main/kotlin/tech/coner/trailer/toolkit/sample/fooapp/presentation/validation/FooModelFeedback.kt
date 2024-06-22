package tech.coner.trailer.toolkit.sample.fooapp.presentation.validation

import tech.coner.trailer.toolkit.sample.fooapp.domain.entity.Foo
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooModel
import tech.coner.trailer.toolkit.validation.Feedback
import tech.coner.trailer.toolkit.validation.FeedbackDelegate
import tech.coner.trailer.toolkit.validation.adapter.propertyAdapterOf

data class FooModelFeedback(val source: FooFeedback)
    : Feedback<FooModel> by FeedbackDelegate(
    feedback = source,
    propertyAdapter = propertyAdapterOf(
        Foo::id to FooModel::id,
        Foo::name to FooModel::name,
        null to null,
    )
)
