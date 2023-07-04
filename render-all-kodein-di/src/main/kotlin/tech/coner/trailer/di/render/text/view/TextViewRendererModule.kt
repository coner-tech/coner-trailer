package tech.coner.trailer.di.render.text.view

import org.kodein.di.*
import tech.coner.trailer.Policy
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.di.render.text.property.textPropertyRenderModule
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.render.property.SignagePropertyRenderer
import tech.coner.trailer.render.text.view.TextPersonViewRenderer
import tech.coner.trailer.render.text.view.TextParticipantsViewRenderer
import tech.coner.trailer.render.text.view.TextRunsViewRenderer
import tech.coner.trailer.render.text.view.eventresults.*
import tech.coner.trailer.render.view.ParticipantsViewRenderer
import tech.coner.trailer.render.view.PersonCollectionViewRenderer
import tech.coner.trailer.render.view.PersonViewRenderer
import tech.coner.trailer.render.view.RunsViewRenderer
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

val textViewRendererModule = DI.Module("tech.coner.trailer.render.text.view") {
    import(textPropertyRenderModule)
    val format = Format.TEXT
    val lineSeparator = System.lineSeparator()
    bindSingleton<PersonViewRenderer>(format) { instance<PersonCollectionViewRenderer>(format) }
    bindSingleton<PersonCollectionViewRenderer>(format) {
        TextPersonViewRenderer(
            lineSeparator = lineSeparator,
            asciiTableFactory = provider()
        )
    }
    bindFactory<Policy, EventResultsViewRenderer<OverallEventResults>>(format) {
        TextOverallEventResultsViewRenderer(
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format)
        )
    }
    bindFactory<Policy, EventResultsViewRenderer<ClassEventResults>>(format) {
        MordantClassEventResultsViewRenderer(
            terminal = instance(),
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format)
        )
    }
    bindFactory<Policy, EventResultsViewRenderer<TopTimesEventResults>>(format) {
        TextTopTimesEventResultsViewRenderer(
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format)
        )
    }
    bindFactory<Policy, EventResultsViewRenderer<ComprehensiveEventResults>>(format) {
        TextComprehensiveEventResultsViewRenderer(
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format),
            overallRenderer = factory<Policy, EventResultsViewRenderer<OverallEventResults>>(format).invoke(it),
            classRenderer = factory<Policy, EventResultsViewRenderer<ClassEventResults>>(format).invoke(it),
            topTimesRenderer = factory<Policy, EventResultsViewRenderer<TopTimesEventResults>>(format).invoke(it)
        )
    }
    bindFactory<Policy, EventResultsViewRenderer<IndividualEventResults>>(format) {
        TextIndividualEventResultsViewRenderer(
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it),
            participantNamePropertyRenderer = instance(format),
            carModelPropertyRenderer = instance(format),
            participantResultDiffPropertyRenderer = instance(format),
            participantResultScoreRenderer = instance(format)
        )
    }
    bindFactory<Policy, RunsViewRenderer>(format) {
        TextRunsViewRenderer(
            runSequencePropertyRenderer = instance(format),
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it),
            carModelPropertyRenderer = instance(format),
            carColorPropertyRenderer = instance(format),
            nullableParticipantNamePropertyRenderer = instance(format),
            nullableTimePropertyRenderer = instance(format),
            penaltiesPropertyRenderer = instance(format),
            runRerunPropertyRenderer = instance(format)
        )
    }
    bindFactory<Policy, ParticipantsViewRenderer>(format) {
        TextParticipantsViewRenderer(
            signagePropertyRenderer = factory<Policy, SignagePropertyRenderer>(format).invoke(it)
        )
    }
}