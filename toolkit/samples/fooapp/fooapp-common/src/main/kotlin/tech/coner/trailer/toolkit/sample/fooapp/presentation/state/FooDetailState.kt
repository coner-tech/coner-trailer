package tech.coner.trailer.toolkit.sample.fooapp.presentation.state

import tech.coner.trailer.toolkit.presentation.model.Loadable
import tech.coner.trailer.toolkit.presentation.state.LoadableState
import tech.coner.trailer.toolkit.sample.fooapp.domain.service.FooService
import tech.coner.trailer.toolkit.sample.fooapp.domain.validation.FooFeedback
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooItemModel
import tech.coner.trailer.toolkit.sample.fooapp.presentation.model.FooModel
import tech.coner.trailer.toolkit.sample.fooapp.presentation.validation.FooModelFeedback

data class FooDetailState(
    override val loadable: Loadable<FooService.FindFailure, FooModel, FooItemModel, FooModelFeedback> = Loadable.Empty()
) : LoadableState<FooService.FindFailure, FooModel, FooItemModel, FooModelFeedback> {
}
