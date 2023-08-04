package tech.coner.trailer.presentation.di.view.text

import org.kodein.di.*
import tech.coner.trailer.presentation.di.adapter.presentationAdapterModule
import tech.coner.trailer.presentation.model.*
import tech.coner.trailer.presentation.model.eventresults.*
import tech.coner.trailer.presentation.text.view.*
import tech.coner.trailer.presentation.text.view.eventresults.*

val textWidgetModule = DI.Module("tech.coner.trailer.text.view") {
    import(presentationAdapterModule)
    val lineSeparator = System.lineSeparator()

    // Bindings for package: tech.coner.trailer.render.text.view

    // Club
    bindSingleton<TextView<ClubModel>> {
        TextClubView()
    }

    // Events
    bindSingleton { new(::TextEventWidgets) }
    bindSingleton<TextView<EventDetailModel>> { instance<TextEventWidgets>().single }
    bindSingleton<TextCollectionView<EventDetailModel, EventDetailCollectionModel>> { instance<TextEventWidgets>().collection }

    // Participants
    bindSingleton<TextCollectionView<ParticipantModel, ParticipantCollectionModel>> {
        TextParticipantsView()
    }

    // Person
    bindSingleton {
        TextPersonViews(
            lineSeparator = lineSeparator,
            asciiTableFactory = provider()
        )
    }
    bindSingleton<TextView<PersonDetailModel>> { instance<TextPersonViews>().single }
    bindSingleton<TextCollectionView<PersonDetailModel, PersonCollectionModel>> { instance<TextPersonViews>().collection }

    // Policy
    bindSingleton { TextPolicyWidgets(lineSeparator) }
    bindSingleton<TextView<PolicyModel>> { instance<TextPolicyWidgets>().single }
    bindSingleton<TextCollectionView<PolicyModel, PolicyCollectionModel>> { instance<TextPolicyWidgets>().collection }

    // Runs
    bindSingleton<TextCollectionView<RunModel, RunCollectionModel>> {
        TextRunsView()
    }

    // Bindings for package: tech.coner.trailer.render.text.view.eventresults

    // Event Results
    bindSingleton<TextView<ClassEventResultsModel>> {
        MordantClassEventResultsView(
            terminal = instance()
        )
    }
    bindSingleton<TextView<ComprehensiveEventResultsModel>> {
        TextComprehensiveEventResultsView(
            lineSeparator = lineSeparator,
            overallEventResultsView = instance(),
            classEventResultsView = instance(),
            topTimesEventResultsView = instance()
        )
    }
    bindSingleton<TextView<IndividualEventResultsModel>> {
        TextIndividualEventResultsView()
    }
    bindSingleton<TextView<OverallEventResultsModel>> {
        TextOverallEventResultsView()
    }
    bindSingleton<TextView<TopTimesEventResultsModel>> {
        TextTopTimesEventResultsView()
    }

}