package tech.coner.trailer.presentation.di.adapter

import org.kodein.di.*
import tech.coner.trailer.*
import tech.coner.trailer.di.DataSessionScope
import tech.coner.trailer.presentation.adapter.*
import tech.coner.trailer.presentation.adapter.eventresults.*
import tech.coner.trailer.presentation.model.*

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
    bindSingleton { new(::EventCrispyFishPeopleMapModelAdapter) }

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

    // Run
    bindSingleton { new(::RunSequenceStringFieldAdapter) }
    bindSingleton { new(::RunRerunStringFieldAdapter) }
    bindSingleton { new(::RunModelAdapter) }
    bindSingleton { new(::RunCollectionModelAdapter) }

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