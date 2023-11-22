package tech.coner.trailer.presentation.di.view.text

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.new
import org.kodein.di.provider
import tech.coner.trailer.presentation.di.adapter.presentationAdapterModule
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
import tech.coner.trailer.presentation.text.view.TextClubView
import tech.coner.trailer.presentation.text.view.TextCollectionView
import tech.coner.trailer.presentation.text.view.TextEventWidgets
import tech.coner.trailer.presentation.text.view.TextParticipantsView
import tech.coner.trailer.presentation.text.view.TextPersonViews
import tech.coner.trailer.presentation.text.view.TextPolicyWidgets
import tech.coner.trailer.presentation.text.view.TextRunsView
import tech.coner.trailer.presentation.text.view.TextView
import tech.coner.trailer.presentation.text.view.eventresults.MordantClassEventResultsView
import tech.coner.trailer.presentation.text.view.eventresults.TextComprehensiveEventResultsView
import tech.coner.trailer.presentation.text.view.eventresults.TextIndividualEventResultsView
import tech.coner.trailer.presentation.text.view.eventresults.TextOverallEventResultsView
import tech.coner.trailer.presentation.text.view.eventresults.TextTopTimesEventResultsView

val textViewModule = DI.Module("tech.coner.trailer.presentation.view.text") {
    import(presentationAdapterModule)
    val lineSeparator = System.lineSeparator()

    // Bindings for package: tech.coner.trailer.presentation.view.text

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
    bindSingleton<MordantClassEventResultsView> { MordantClassEventResultsView(
        terminal = instance()
    ) }
    bindSingleton<TextView<ClassEventResultsModel>> {
        instance<MordantClassEventResultsView>()
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
    bindSingleton<TextOverallEventResultsView> { TextOverallEventResultsView() }
    bindSingleton<TextView<OverallEventResultsModel>> {
        instance<TextOverallEventResultsView>()
    }
    bindSingleton<TextTopTimesEventResultsView> {
        TextTopTimesEventResultsView()
    }
    bindSingleton<TextView<TopTimesEventResultsModel>> {
        instance<TextTopTimesEventResultsView>()
    }

}