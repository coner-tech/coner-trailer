package tech.coner.trailer.presentation.di.adapter

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.new
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.Class
import tech.coner.trailer.Club
import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.Person
import tech.coner.trailer.Policy
import tech.coner.trailer.Run
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.io.model.PolicyCollection
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.ArbitraryRunsCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.CarColorStringFieldAdapter
import tech.coner.trailer.presentation.adapter.CarModelStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClassAbbreviationStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClassModelAdapter
import tech.coner.trailer.presentation.adapter.ClassNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClassParentModelAdapter
import tech.coner.trailer.presentation.adapter.ClassParentNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClassPaxFactorStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClassPaxedStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClassSortStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ClubModelAdapter
import tech.coner.trailer.presentation.adapter.ClubNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventContextRunCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.EventCrispyFishClassDefinitionFileStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventCrispyFishEventControlFileStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventCrispyFishPeopleMapModelAdapter
import tech.coner.trailer.presentation.adapter.EventDateStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventDetailCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.adapter.EventIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventLifecycleStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventMotorsportRegIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.EventRunLatestCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.EventRunLatestModelAdapter
import tech.coner.trailer.presentation.adapter.NullableParticipantFullNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.NullableTimeStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ParticipantClubMemberIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ParticipantCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.ParticipantFirstNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ParticipantFullNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ParticipantLastNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.ParticipantModelAdapter
import tech.coner.trailer.presentation.adapter.PenaltyCollectionStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PenaltyStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PersonClubMemberIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PersonCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.PersonDetailModelAdapter
import tech.coner.trailer.presentation.adapter.PersonFirstNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PersonFullNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PersonIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PersonLastNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PersonMotorsportRegMemberIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PolicyCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.PolicyConePenaltySecondsStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PolicyFinalScoreStyleStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PolicyIdStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PolicyModelAdapter
import tech.coner.trailer.presentation.adapter.PolicyNameStringFieldAdapter
import tech.coner.trailer.presentation.adapter.PolicyPaxTimeStyleStringFieldAdapter
import tech.coner.trailer.presentation.adapter.RunModelAdapter
import tech.coner.trailer.presentation.adapter.RunRerunStringFieldAdapter
import tech.coner.trailer.presentation.adapter.RunSequenceStringFieldAdapter
import tech.coner.trailer.presentation.adapter.SignageStringFieldAdapter
import tech.coner.trailer.presentation.adapter.TimeStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ClassEventResultsModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ClassParticipantResultsCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ComprehensiveEventResultsModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.EventResultsTypeKeyStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.EventResultsTypeModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.EventResultsTypeScoreColumnHeadingStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.EventResultsTypeTitleStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.IndividualEventResultsCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.IndividualEventResultsModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.IndividualInnerEventResultsTypesCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.OverallEventResultsModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.OverallParticipantResultsCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ParticipantResultDiffStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ParticipantResultModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ParticipantResultPositionStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.ParticipantResultScoreStringFieldAdapter
import tech.coner.trailer.presentation.adapter.eventresults.TopTimesEventResultsCollectionModelAdapter
import tech.coner.trailer.presentation.adapter.eventresults.TopTimesEventResultsModelAdapter
import tech.coner.trailer.presentation.model.ClassModel
import tech.coner.trailer.presentation.model.ClassParentModel
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.model.EventDetailCollectionModel
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.model.EventRunLatestModel
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.state.EventRunLatestState

val presentationAdapterModule = DI.Module("tech.coner.trailer.presentation.adapter") {
    // Bindings for package: tech.coner.trailer.render.adapter

    // Car
    bindSingleton { new(::CarModelStringFieldAdapter) }
    bindSingleton { new(::CarColorStringFieldAdapter) }

    // Class
    bindSingleton { new(::ClassAbbreviationStringFieldAdapter) }
    bindSingleton { new(::ClassNameStringFieldAdapter) }
    bindSingleton { new(::ClassSortStringFieldAdapter) }
    bindSingleton { new(::ClassPaxedStringFieldAdapter) }
    bindSingleton { new(::ClassPaxFactorStringFieldAdapter) }
    bindSingleton { new(::ClassModelAdapter) }
    bindSingleton<Adapter<Class, ClassModel>> { instance<ClassModelAdapter>() }

    // Class Parent
    bindSingleton { new(::ClassParentNameStringFieldAdapter) }
    bindSingleton { new(::ClassParentModelAdapter) }
    bindSingleton<Adapter<Class.Parent, ClassParentModel>> { instance<ClassParentModelAdapter>() }

    // Club
    bind { scoped(DataSessionScope).singleton { new(::ClubModelAdapter) } }
    bind<Adapter<Club, ClubModel>> { scoped(DataSessionScope).singleton { instance<ClubModelAdapter>() } }
    bindSingleton { new(::ClubNameStringFieldAdapter) }

    // Event
    bindSingleton { new(::EventIdStringFieldAdapter) }
    bindSingleton { new(::EventNameStringFieldAdapter) }
    bindSingleton { new(::EventDateStringFieldAdapter) }
    bindSingleton { new(::EventLifecycleStringFieldAdapter) }
    bindSingleton { new(::EventCrispyFishEventControlFileStringFieldAdapter) }
    bindSingleton { new(::EventCrispyFishClassDefinitionFileStringFieldAdapter) }
    bindSingleton { new(::EventMotorsportRegIdStringFieldAdapter) }
    bindSingleton { new(::EventDetailModelAdapter) }
    bindSingleton<Adapter<Event, EventDetailModel>> { instance<EventDetailModelAdapter>() }
    bindSingleton { new(::EventDetailCollectionModelAdapter) }
    bindSingleton<Adapter<Collection<Event>, EventDetailCollectionModel>> { instance<EventDetailCollectionModelAdapter>() }
    bindSingleton { new(::EventCrispyFishPeopleMapModelAdapter) }

    // Event Run Latest
    bindSingleton<Adapter<EventRunLatestState, EventRunLatestModel>> { instance<EventRunLatestModelAdapter>() }
    bindSingleton { new(::EventRunLatestModelAdapter) }
    bindSingleton<Adapter<EventRunLatestState, RunCollectionModel>> { instance<EventRunLatestCollectionModelAdapter>() }
    bindSingleton { new(::EventRunLatestCollectionModelAdapter) }

    // Participant
    bindSingleton { new(::ParticipantFirstNameStringFieldAdapter) }
    bindSingleton { new(::ParticipantLastNameStringFieldAdapter) }
    bindSingleton { new(::ParticipantFullNameStringFieldAdapter) }
    bindSingleton { new(::NullableParticipantFullNameStringFieldAdapter) }
    bindSingleton { new(::ParticipantClubMemberIdStringFieldAdapter) }
    bindSingleton { new(::ParticipantModelAdapter) }
    bindSingleton { new(::ParticipantCollectionModelAdapter) }

    // Penalty
    bindSingleton { new(::PenaltyStringFieldAdapter) }
    bindSingleton { new(::PenaltyCollectionStringFieldAdapter) }

    // Person
    bindSingleton { new(::PersonIdStringFieldAdapter) }
    bindSingleton { new(::PersonFirstNameStringFieldAdapter) }
    bindSingleton { new(::PersonLastNameStringFieldAdapter) }
    bindSingleton { new(::PersonFullNameStringFieldAdapter) }
    bindSingleton { new(::PersonClubMemberIdStringFieldAdapter) }
    bindSingleton { new(::PersonMotorsportRegMemberIdStringFieldAdapter) }
    bind { scoped(DataSessionScope).singleton { new(::PersonDetailModelAdapter) } }
    bind<Adapter<Person, PersonDetailModel>> { scoped(DataSessionScope).singleton { instance<PersonDetailModelAdapter>() } }
    bind<Adapter<Collection<Person>, PersonCollectionModel>> { scoped(DataSessionScope).singleton { new(::PersonCollectionModelAdapter) } }

    // Policy
    bindSingleton { new(::PolicyIdStringFieldAdapter) }
    bindSingleton { new(::PolicyNameStringFieldAdapter) }
    bindSingleton { new(::PolicyConePenaltySecondsStringFieldAdapter) }
    bindSingleton { new(::PolicyPaxTimeStyleStringFieldAdapter) }
    bindSingleton { new(::PolicyFinalScoreStyleStringFieldAdapter) }
    bind { scoped(DataSessionScope).singleton { new(::PolicyModelAdapter) } }
    bind<Adapter<Policy, PolicyModel>> { scoped(DataSessionScope).singleton { instance<PolicyModelAdapter>() } }
    bind { scoped(DataSessionScope).singleton { new(::PolicyCollectionModelAdapter) } }
    bind<Adapter<PolicyCollection, PolicyCollectionModel>> { scoped(DataSessionScope).singleton { instance<PolicyCollectionModelAdapter>() } }

    // Run
    bindSingleton { new(::RunSequenceStringFieldAdapter) }
    bindSingleton { new(::RunRerunStringFieldAdapter) }
    bindSingleton { new(::RunModelAdapter) }
    bindSingleton { new(::EventContextRunCollectionModelAdapter) }
    bind<Adapter<EventContext, RunCollectionModel>> { scoped(DataSessionScope).singleton { instance<EventContextRunCollectionModelAdapter>() } }
    bind<Adapter<Pair<Event, Collection<Run>>, RunCollectionModel>> { scoped(DataSessionScope).singleton { new(::ArbitraryRunsCollectionModelAdapter) } }

    // Signage
    bindSingleton { new(::SignageStringFieldAdapter) }

    // Time
    bindSingleton { new(::TimeStringFieldAdapter) }
    bindSingleton { new(::NullableTimeStringFieldAdapter) }

    // Bindings for package: tech.coner.trailer.render.adapter.eventresults

    // ClassEventResults
    bindSingleton { new(::ClassEventResultsModelAdapter) }
    bindSingleton { new(::ClassParticipantResultsCollectionModelAdapter) }

    // ComprehensiveEventResults
    bindSingleton { new(::ComprehensiveEventResultsModelAdapter) }

    // EventResults
    bindSingleton { new(::EventResultsTypeKeyStringFieldAdapter) }
    bindSingleton { new(::EventResultsTypeTitleStringFieldAdapter) }
    bindSingleton { new(::EventResultsTypeScoreColumnHeadingStringFieldAdapter) }

    // EventResultsType
    bindSingleton { new(::EventResultsTypeModelAdapter) }

    // IndividualEventResults
    bindSingleton { new(::IndividualEventResultsModelAdapter) }
    bindSingleton { new(::IndividualInnerEventResultsTypesCollectionModelAdapter) }
    bindSingleton { new(::IndividualEventResultsCollectionModelAdapter) }

    // OverallEventResults
    bindSingleton { new(::OverallEventResultsModelAdapter) }
    bindSingleton { new(::OverallParticipantResultsCollectionModelAdapter) }

    // ParticipantResult
    bindSingleton { new(::ParticipantResultPositionStringFieldAdapter) }
    bindSingleton { new(::ParticipantResultDiffStringFieldAdapter) }
    bindSingleton { new(::ParticipantResultScoreStringFieldAdapter) }
    bindSingleton { new(::ParticipantResultModelAdapter) }

    // TopTimes
    bindSingleton { new(::TopTimesEventResultsModelAdapter) }
    bindSingleton { new(::TopTimesEventResultsCollectionModelAdapter) }
}