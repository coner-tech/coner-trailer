package tech.coner.trailer.di.render.text.view

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.provider
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.di.render.text.property.textPropertyRenderModule
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.text.view.TextClubViewRenderer
import tech.coner.trailer.render.text.view.TextEventCollectionViewRenderer
import tech.coner.trailer.render.text.view.TextEventViewRenderer
import tech.coner.trailer.render.text.view.TextParticipantsViewRenderer
import tech.coner.trailer.render.text.view.TextPersonViewRenderer
import tech.coner.trailer.render.text.view.TextPolicyViewRenderer
import tech.coner.trailer.render.text.view.TextRunsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.MordantClassEventResultsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.TextComprehensiveEventResultsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.TextIndividualEventResultsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.TextOverallEventResultsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.TextTopTimesEventResultsViewRenderer
import tech.coner.trailer.render.view.ClubViewRenderer
import tech.coner.trailer.render.view.EventCollectionViewRenderer
import tech.coner.trailer.render.view.EventViewRenderer
import tech.coner.trailer.render.view.ParticipantsViewRenderer
import tech.coner.trailer.render.view.PersonCollectionViewRenderer
import tech.coner.trailer.render.view.PersonViewRenderer
import tech.coner.trailer.render.view.PolicyCollectionViewRenderer
import tech.coner.trailer.render.view.PolicyViewRenderer
import tech.coner.trailer.render.view.RunsViewRenderer
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

    // Events
    bindSingleton<EventViewRenderer>(format) {
        TextEventViewRenderer(
            asciiTableFactory = provider(),
            eventIdPropertyRenderer = instance(format),
            eventNamePropertyRenderer = instance(format),
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
    bindSingleton<EventCollectionViewRenderer>(format) {
        TextEventCollectionViewRenderer(
            terminal = instance(),
            eventIdPropertyRenderer = instance(format),
            eventNamePropertyRenderer = instance(format),
            eventDatePropertyRenderer = instance(format),
            policyNamePropertyRenderer = instance(format)
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

    // Policy
    bindSingleton<PolicyViewRenderer>(format) { instance<PolicyCollectionViewRenderer>(format) }
    bindSingleton<PolicyCollectionViewRenderer>(format) {
        TextPolicyViewRenderer(
            lineSeparator = lineSeparator,
            policyIdPropertyRenderer = instance(format),
            policyNamePropertyRenderer = instance(format),
            policyConePenaltySecondsPropertyRenderer = instance(format),
            policyPaxTimeStylePropertyRenderer = instance(format),
            policyFinalScoreStylePropertyRenderer = instance(format)
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