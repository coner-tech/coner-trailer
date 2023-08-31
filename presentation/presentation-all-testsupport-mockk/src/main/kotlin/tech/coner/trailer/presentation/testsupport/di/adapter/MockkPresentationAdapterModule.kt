package tech.coner.trailer.presentation.testsupport.di.adapter

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.Policy
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.model.PolicyModel

val mockkPresentationAdapterModule = DI.Module("tech.coner.trailer.presentation.adapter mockk") {

    // Event Detail
    bind<EventDetailModelAdapter> { scoped(DataSessionScope).singleton { mockk() } }

    // Policy
    bind<Adapter<Policy, PolicyModel>> { scoped(DataSessionScope).singleton { mockk() } }
}