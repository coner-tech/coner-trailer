package tech.coner.trailer.di.render.text.property

import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer
import tech.coner.trailer.render.text.property.*
import tech.coner.trailer.render.text.property.eventresults.TextParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.text.property.eventresults.TextParticipantResultScoreRenderer

val textPropertyRenderModule = DI.Module("tech.coner.trailer.render.text.property") {
    val format = Format.TEXT
    bindSingleton<RunSequencePropertyRenderer>(format) { TextRunSequencePropertyRenderer() }
    bindFactory<Policy, SignagePropertyRenderer>(format) { TextSignagePropertyRenderer(policy = it) }
    bindSingleton<ParticipantNamePropertyRenderer>(format) { TextParticipantNamePropertyRenderer() }
    bindSingleton<NullableParticipantNamePropertyRenderer>(format) {
        TextNullableParticipantNamePropertyRenderer(
            participantNamePropertyRenderer = instance(format),
        )
    }
    bindSingleton<CarModelPropertyRenderer>(format) { TextCarModelPropertyRenderer() }
    bindSingleton<CarColorPropertyRenderer>(format) { TextCarColorPropertyRenderer() }
    bindSingleton<TimePropertyRenderer>(format) { TextTimePropertyRenderer() }
    bindSingleton<NullableTimePropertyRenderer>(format) {
        TextNullableTimePropertyRenderer(
            timePropertyRenderer = instance(format)
        )
    }
    bindSingleton<PenaltyPropertyRenderer>(format) { TextPenaltyPropertyRenderer() }
    bindSingleton<PenaltiesPropertyRenderer>(format) {
        TextPenaltiesPropertyRenderer(
            penaltyPropertyRenderer = instance(format)
        )
    }
    bindSingleton<RunRerunPropertyRenderer>(format) { TextRunRerunPropertyRenderer() }
    bindSingleton<ParticipantResultDiffPropertyRenderer>(format) {
        TextParticipantResultDiffPropertyRenderer(
            nullableTimePropertyRenderer = instance(format),
        )
    }
    bindSingleton<ParticipantResultScoreRenderer>(format) { TextParticipantResultScoreRenderer() }
}