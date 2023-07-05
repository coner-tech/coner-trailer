package tech.coner.trailer.di.render.text.view

import org.kodein.di.*
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.di.render.text.property.textPropertyRenderModule
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.text.view.*
import tech.coner.trailer.render.text.view.eventresults.*
import tech.coner.trailer.render.view.*
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

val textViewRendererModule = DI.Module("tech.coner.trailer.render.text.view") {
    import(textPropertyRenderModule)
    val format = Format.TEXT
    val lineSeparator = System.lineSeparator()

    // Club
    bindSingleton<ClubViewRenderer>(format) {
        TextClubViewRenderer(
            clubNamePropertyRenderer = instance(format)
        )
    }

    // Person
    bindSingleton<PersonViewRenderer>(format) { instance<PersonCollectionViewRenderer>(format) }
    bindSingleton<PersonCollectionViewRenderer>(format) {
        TextPersonViewRenderer(
            lineSeparator = lineSeparator,
            asciiTableFactory = provider(),
            personIdPropertyRenderer = instance(format),
            personFirstNamePropertyRenderer = instance(format),
            personLastNamePropertyRenderer = instance(format),
            personClubMemberIdPropertyRenderer = instance(format),
            personMotorsportRegMemberIdPropertyRenderer = instance(format)
        )
    }

    // Events
    bindSingleton<EventViewRenderer>(format) {
        TextEventViewRenderer(
            asciiTableFactory = provider(),
            eventIdPropertyRenderer = instance(format),
            eventDatePropertyRenderer = instance(format),
            eventLifecyclePropertyRenderer = instance(format),
            eventCrispyFishEventControlFilePropertyRenderer = instance(format),
            eventCrispyFishClassDefinitionFilePropertyRenderer = instance(format),
            eventMotorsportRegIdPropertyRenderer = instance(format),
            policyIdPropertyRenderer = instance(format),
            policyNamePropertyRenderer = instance(format),
            signagePropertyRenderer = instance(format),
            personIdPropertyRenderer = instance(format)
        )
    }

    // Event Results
    bindSingleton<EventResultsViewRenderer<OverallEventResults>>(format) {
        TextOverallEventResultsViewRenderer(
            signagePropertyRenderer = instance(format),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format)
        )
    }
    bindSingleton<EventResultsViewRenderer<ClassEventResults>>(format) {
        MordantClassEventResultsViewRenderer(
            terminal = instance(),
            signagePropertyRenderer = instance(format),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format)
        )
    }
    bindSingleton<EventResultsViewRenderer<TopTimesEventResults>>(format) {
        TextTopTimesEventResultsViewRenderer(
            signagePropertyRenderer = instance(format),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format)
        )
    }
    bindSingleton<EventResultsViewRenderer<ComprehensiveEventResults>>(format) {
        TextComprehensiveEventResultsViewRenderer(
            signagePropertyRenderer = instance(format),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format),
            overallRenderer = instance(format),
            classRenderer = instance(format),
            topTimesRenderer = instance(format)
        )
    }
    bindSingleton<EventResultsViewRenderer<IndividualEventResults>>(format) {
        TextIndividualEventResultsViewRenderer(
            signagePropertyRenderer = instance(format),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format)
        )
    }

    // Participants
    bindSingleton<ParticipantsViewRenderer>(format) {
        TextParticipantsViewRenderer(
            signagePropertyRenderer = instance(format)
        )
    }

    // Runs
    bindSingleton<RunsViewRenderer>(format) {
        TextRunsViewRenderer(
            runSequencePropertyRenderer = instance(format),
            signagePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            carColorPropertyRenderer = instance(format),
            nullableParticipantNamePropertyRenderer = instance(format),
            nullableTimePropertyRenderer = instance(format),
            penaltiesPropertyRenderer = instance(format),
            runRerunPropertyRenderer = instance(format)
        )
    }
}