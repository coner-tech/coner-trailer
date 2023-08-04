package tech.coner.trailer.presentation.testsupport.di.view.text

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.presentation.model.*
import tech.coner.trailer.presentation.model.eventresults.*
import tech.coner.trailer.presentation.text.view.*
import tech.coner.trailer.presentation.text.view.eventresults.*

val mockkTextViewModule = DI.Module("tech.coner.trailer.testsupport.render.text.view") {
    // MockK bindings for package: tech.coner.trailer.render.text.view

    // Club
    bindSingleton<TextView<ClubModel>> { mockk() }

    // Events
    bindSingleton<TextView<EventDetailModel>> { mockk() }

    // Participants
    bindSingleton<TextView<ParticipantModel>> { mockk() }

    // Person
    bindSingleton<TextView<PersonDetailModel>> { mockk() }

    // Policy
    bindSingleton<TextView<PolicyModel>> { mockk() }

    // Runs
    bindSingleton<TextView<RunCollectionModel>> { mockk() }

    // MockK bindings for package: tech.coner.trailer.render.text.view.eventresults

    // Event Results
    bindSingleton<TextView<ClassEventResultsModel>> { mockk() }
    bindSingleton<TextView<ComprehensiveEventResultsModel>> { mockk() }
    bindSingleton<TextView<IndividualEventResultsModel>> { mockk() }
    bindSingleton<TextView<OverallEventResultsModel>> { mockk() }
    bindSingleton<TextView<TopTimesEventResultsModel>> { mockk() }
}