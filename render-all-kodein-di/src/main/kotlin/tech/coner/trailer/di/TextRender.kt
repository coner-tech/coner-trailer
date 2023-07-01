package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.Policy
import tech.coner.trailer.render.ParticipantsRenderer
import tech.coner.trailer.render.RunsRenderer
import tech.coner.trailer.render.eventresults.*
import tech.coner.trailer.render.internal.*
import tech.coner.trailer.render.text.TextParticipantsRenderer
import tech.coner.trailer.render.text.TextRunsRenderer
import tech.coner.trailer.render.text.eventresults.*
import tech.coner.trailer.render.text.internal.*

val textRenderModule = DI.Module("tech.coner.trailer.render.text") {
    val format = Format.TEXT
    bindFactory<Policy, OverallEventResultsRenderer>(format) {
        TextOverallEventResultsRenderer(
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it),
            participantNameRenderer = internalDirect.instance(),
            carModelRenderer = internalDirect.instance(),
            participantResultDiffRenderer = internalDirect.instance(),
            participantResultScoreRenderer = internalDirect.instance()
        )
    }
    bindFactory<Policy, ClassEventResultsRenderer>(format) {
        MordantClassEventResultsRenderer(
            terminal = instance(),
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it),
            participantNameRenderer = internalDirect.instance(),
            carModelRenderer = internalDirect.instance(),
            participantResultScoreRenderer = internalDirect.instance(),
            participantResultDiffRenderer = internalDirect.instance()
        )
    }
    bindFactory<Policy, TopTimesEventResultsRenderer>(format) {
        TextTopTimesEventResultsRenderer(
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it),
            participantNameRenderer = internalDirect.instance(),
            carModelRenderer = internalDirect.instance(),
            participantResultScoreRenderer = internalDirect.instance(),
            participantResultDiffRenderer = internalDirect.instance()
        )
    }
    bindFactory<Policy, ComprehensiveEventResultsRenderer>(format) {
        TextComprehensiveEventResultsRenderer(
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it),
            participantNameRenderer = internalDirect.instance(),
            carModelRenderer = internalDirect.instance(),
            participantResultDiffRenderer = internalDirect.instance(),
            participantResultScoreRenderer = internalDirect.instance(),
            overallRenderer = factory<Policy, OverallEventResultsRenderer>(format).invoke(it),
            classRenderer = factory<Policy, ClassEventResultsRenderer>(format).invoke(it),
            topTimesRenderer = factory<Policy, TopTimesEventResultsRenderer>(format).invoke(it)
        )
    }
    bindFactory<Policy, IndividualEventResultsRenderer>(format) {
        TextIndividualEventResultsRenderer(
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it),
            participantNameRenderer = internalDirect.instance(),
            carModelRenderer = internalDirect.instance(),
            participantResultDiffRenderer = internalDirect.instance(),
            participantResultScoreRenderer = internalDirect.instance()
        )
    }
    bindFactory<Policy, RunsRenderer>(format) {
        TextRunsRenderer(
            runSequenceRenderer = internalDirect.instance(),
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it),
            carModelRenderer = internalDirect.instance(),
            carColorRenderer = internalDirect.instance(),
            nullableParticipantNameRenderer = internalDirect.instance(),
            nullableTimeRenderer = internalDirect.instance(),
            penaltiesRenderer = internalDirect.instance(),
            runRerunRenderer = internalDirect.instance()
        )
    }
    bindFactory<Policy, ParticipantsRenderer>(format) {
        TextParticipantsRenderer(
            signageRenderer = internalDirect.factory<Policy, SignageRenderer>().invoke(it)
        )
    }
}

private val internal = DI {
    bindSingleton<RunSequenceRenderer> { TextRunSequenceRenderer() }
    bindFactory<Policy, SignageRenderer> { TextSignageRenderer(policy = it) }
    bindSingleton<ParticipantNameRenderer> { TextParticipantNameRenderer() }
    bindSingleton<NullableParticipantNameRenderer> {
        TextNullableParticipantNameRenderer(
            participantNameRenderer = instance(),
        )
    }
    bindSingleton<CarModelRenderer> { TextCarModelRenderer() }
    bindSingleton<CarColorRenderer> { TextCarColorRenderer() }
    bindSingleton<TimeRenderer> { TextTimeRenderer() }
    bindSingleton<NullableTimeRenderer> {
        TextNullableTimeRenderer(
            timeRenderer = instance()
        )
    }
    bindSingleton<PenaltyRenderer> { TextPenaltyRenderer() }
    bindSingleton<PenaltiesRenderer> {
        TextPenaltiesRenderer(
            penaltyRenderer = instance()
        )
    }
    bindSingleton<RunRerunRenderer> { TextRunRerunRenderer() }
    bindSingleton<ParticipantResultDiffRenderer> {
        TextParticipantResultDiffRenderer(
            nullableTimeRenderer = instance(),
        )
    }
    bindSingleton<ParticipantResultScoreRenderer> { TextParticipantResultScoreRenderer() }
}
private val internalDirect = internal.direct