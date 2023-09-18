package tech.coner.trailer.presentation.testsupport.di.adapter

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.Person
import tech.coner.trailer.Policy
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.io.model.PolicyCollection
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.model.eventresults.*

val mockkPresentationAdapterModule = DI.Module("tech.coner.trailer.presentation.adapter mockk") {

    // Event Detail
    bind<EventDetailModelAdapter> { scoped(DataSessionScope).singleton { mockk() } }

    // Event Results
    bind<Adapter<OverallEventResults, OverallEventResultsModel>> { singleton { mockk() } }
    bind<Adapter<ClassEventResults, ClassEventResultsModel>> { singleton { mockk() } }
    bind<Adapter<TopTimesEventResults, TopTimesEventResultsModel>> { singleton { mockk() } }
    bind<Adapter<ComprehensiveEventResults, ComprehensiveEventResultsModel>> { singleton { mockk() } }
    bind<Adapter<IndividualEventResults, IndividualEventResultsModel>> { singleton { mockk() } }

    // Person
    bind<Adapter<Person, PersonDetailModel>> { singleton { mockk() } }
    bind<Adapter<Collection<Person>, PersonCollectionModel>> { singleton { mockk() } }

    // Policy
    bind<Adapter<Policy, PolicyModel>> { scoped(DataSessionScope).singleton { mockk() } }
    bind<Adapter<PolicyCollection, PolicyCollectionModel>> { scoped(DataSessionScope).singleton { mockk() } }
}