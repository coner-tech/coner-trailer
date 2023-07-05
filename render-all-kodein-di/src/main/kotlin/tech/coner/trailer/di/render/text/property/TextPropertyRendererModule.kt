package tech.coner.trailer.di.render.text.property

import org.kodein.di.*
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
    // Car
    bindSingleton<CarModelPropertyRenderer>(format) { TextCarModelPropertyRenderer() }
    bindSingleton<CarColorPropertyRenderer>(format) { TextCarColorPropertyRenderer() }

    // Club
    bindSingleton<ClubNamePropertyRenderer>(format) { TextClubNamePropertyRenderer() }

    // Participant
    bindSingleton<ParticipantNamePropertyRenderer>(format) { TextParticipantNamePropertyRenderer() }
    bindSingleton<NullableParticipantNamePropertyRenderer>(format) {
        TextNullableParticipantNamePropertyRenderer(
            participantNamePropertyRenderer = instance(format),
        )
    }

    // ParticipantResult
    bindSingleton<ParticipantResultDiffPropertyRenderer>(format) {
        TextParticipantResultDiffPropertyRenderer(
            nullableTimePropertyRenderer = instance(format),
        )
    }
    bindSingleton<ParticipantResultScoreRenderer>(format) { TextParticipantResultScoreRenderer() }

    // Penalty
    bindSingleton<PenaltyPropertyRenderer>(format) { TextPenaltyPropertyRenderer() }
    bindSingleton<PenaltiesPropertyRenderer>(format) {
        TextPenaltiesPropertyRenderer(
            penaltyPropertyRenderer = instance(format)
        )
    }

    // Person
    bindSingleton<PersonIdPropertyRenderer>(format) { TextPersonIdPropertyRenderer() }
    bindSingleton<PersonFirstNamePropertyRenderer>(format) { TextPersonFirstNamePropertyRenderer() }
    bindSingleton<PersonLastNamePropertyRenderer>(format) { TextPersonLastNamePropertyRenderer() }
    bindSingleton<PersonClubMemberIdPropertyRenderer>(format) { TextPersonClubMemberIdPropertyRenderer() }
    bindSingleton<PersonMotorsportRegMemberIdPropertyRenderer>(format) { TextPersonMotorsportRegMemberIdPropertyRenderer() }

    // Run
    bindSingleton<RunSequencePropertyRenderer>(format) { TextRunSequencePropertyRenderer() }
    bindSingleton<RunRerunPropertyRenderer>(format) { TextRunRerunPropertyRenderer() }

    // Signage
    bindSingleton<SignagePropertyRenderer>(format) { TextSignagePropertyRenderer() }

    // Time
    bindSingleton<TimePropertyRenderer>(format) { TextTimePropertyRenderer() }
    bindSingleton<NullableTimePropertyRenderer>(format) {
        TextNullableTimePropertyRenderer(
            timePropertyRenderer = instance(format)
        )
    }
}