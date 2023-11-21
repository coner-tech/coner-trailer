package tech.coner.trailer.presentation.testsupport.di.view.text

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.model.EventDetailCollectionModel
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.model.ParticipantCollectionModel
import tech.coner.trailer.presentation.model.ParticipantModel
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.model.eventresults.ClassEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.ComprehensiveEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.IndividualEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.OverallEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.TopTimesEventResultsModel
import tech.coner.trailer.presentation.text.view.TextCollectionView
import tech.coner.trailer.presentation.text.view.TextView

val mockkTextViewModule = DI.Module("tech.coner.trailer.testsupport.render.text.view") {
    // MockK bindings for package: tech.coner.trailer.render.text.view

    // Club
    bindSingleton<TextView<ClubModel>> { mockk() }

    // Events
    bindSingleton<TextView<EventDetailModel>> { mockk() }
    bindSingleton<TextCollectionView<EventDetailModel, EventDetailCollectionModel>> { mockk() }

    // Participants
    bindSingleton<TextView<ParticipantModel>> { mockk() }
    bindSingleton<TextCollectionView<ParticipantModel, ParticipantCollectionModel>> { mockk() }

    // Person
    bindSingleton<TextView<PersonDetailModel>> { mockk() }
    bindSingleton<TextCollectionView<PersonDetailModel, PersonCollectionModel>> { mockk() }

    // Policy
    bindSingleton<TextView<PolicyModel>> { mockk() }
    bindSingleton<TextCollectionView<PolicyModel, PolicyCollectionModel>> { mockk() }

    // Runs
    bindSingleton<TextView<RunCollectionModel>> { mockk() }
    bindSingleton<TextCollectionView<RunModel, RunCollectionModel>> { mockk() }

    // MockK bindings for package: tech.coner.trailer.render.text.view.eventresults

    // Event Results
    bindSingleton<TextView<ClassEventResultsModel>> { mockk() }
    bindSingleton<TextView<ComprehensiveEventResultsModel>> { mockk() }
    bindSingleton<TextView<IndividualEventResultsModel>> { mockk() }
    bindSingleton<TextView<OverallEventResultsModel>> { mockk() }
    bindSingleton<TextView<TopTimesEventResultsModel>> { mockk() }
}