package tech.coner.trailer.di.render.text.property

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.property.CarColorPropertyRenderer
import tech.coner.trailer.render.property.CarModelPropertyRenderer
import tech.coner.trailer.render.property.ClubNamePropertyRenderer
import tech.coner.trailer.render.property.EventCrispyFishClassDefinitionFilePropertyRenderer
import tech.coner.trailer.render.property.EventCrispyFishEventControlFilePropertyRenderer
import tech.coner.trailer.render.property.EventDatePropertyRenderer
import tech.coner.trailer.render.property.EventIdPropertyRenderer
import tech.coner.trailer.render.property.EventLifecyclePropertyRenderer
import tech.coner.trailer.render.property.EventMotorsportRegIdPropertyRenderer
import tech.coner.trailer.render.property.EventNamePropertyRenderer
import tech.coner.trailer.render.property.NullableParticipantNamePropertyRenderer
import tech.coner.trailer.render.property.NullableTimePropertyRenderer
import tech.coner.trailer.render.property.ParticipantNamePropertyRenderer
import tech.coner.trailer.render.property.PenaltiesPropertyRenderer
import tech.coner.trailer.render.property.PenaltyPropertyRenderer
import tech.coner.trailer.render.property.PersonClubMemberIdPropertyRenderer
import tech.coner.trailer.render.property.PersonFirstNamePropertyRenderer
import tech.coner.trailer.render.property.PersonIdPropertyRenderer
import tech.coner.trailer.render.property.PersonLastNamePropertyRenderer
import tech.coner.trailer.render.property.PersonMotorsportRegMemberIdPropertyRenderer
import tech.coner.trailer.render.property.PolicyConePenaltySecondsPropertyRenderer
import tech.coner.trailer.render.property.PolicyFinalScoreStylePropertyRenderer
import tech.coner.trailer.render.property.PolicyIdPropertyRenderer
import tech.coner.trailer.render.property.PolicyNamePropertyRenderer
import tech.coner.trailer.render.property.PolicyPaxTimeStylePropertyRenderer
import tech.coner.trailer.render.property.RunRerunPropertyRenderer
import tech.coner.trailer.render.property.RunSequencePropertyRenderer
import tech.coner.trailer.render.property.SignagePropertyRenderer
import tech.coner.trailer.render.property.TimePropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer
import tech.coner.trailer.render.text.property.TextCarColorPropertyRenderer
import tech.coner.trailer.render.text.property.TextCarModelPropertyRenderer
import tech.coner.trailer.render.text.property.TextClubNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextEventCrispyFishClassDefinitionFilePropertyRenderer
import tech.coner.trailer.render.text.property.TextEventCrispyFishEventControlFilePropertyRenderer
import tech.coner.trailer.render.text.property.TextEventDatePropertyRenderer
import tech.coner.trailer.render.text.property.TextEventIdPropertyRenderer
import tech.coner.trailer.render.text.property.TextEventLifecyclePropertyRenderer
import tech.coner.trailer.render.text.property.TextEventMotorsportRegIdPropertyRenderer
import tech.coner.trailer.render.text.property.TextEventNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextNullableParticipantNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextNullableTimePropertyRenderer
import tech.coner.trailer.render.text.property.TextParticipantNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextPenaltiesPropertyRenderer
import tech.coner.trailer.render.text.property.TextPenaltyPropertyRenderer
import tech.coner.trailer.render.text.property.TextPersonClubMemberIdPropertyRenderer
import tech.coner.trailer.render.text.property.TextPersonFirstNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextPersonIdPropertyRenderer
import tech.coner.trailer.render.text.property.TextPersonLastNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextPersonMotorsportRegMemberIdPropertyRenderer
import tech.coner.trailer.render.text.property.TextPolicyConePenaltySecondsPropertyRenderer
import tech.coner.trailer.render.text.property.TextPolicyFinalScoreStylePropertyRenderer
import tech.coner.trailer.render.text.property.TextPolicyIdPropertyRenderer
import tech.coner.trailer.render.text.property.TextPolicyNamePropertyRenderer
import tech.coner.trailer.render.text.property.TextPolicyPaxTimeStylePropertyRenderer
import tech.coner.trailer.render.text.property.TextRunRerunPropertyRenderer
import tech.coner.trailer.render.text.property.TextRunSequencePropertyRenderer
import tech.coner.trailer.render.text.property.TextSignagePropertyRenderer
import tech.coner.trailer.render.text.property.TextTimePropertyRenderer
import tech.coner.trailer.render.text.property.eventresults.TextParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.text.property.eventresults.TextParticipantResultScoreRenderer

val textPropertyRenderModule = DI.Module("tech.coner.trailer.render.text.property") {
    val format = Format.TEXT
    // Car
    bindSingleton<CarModelPropertyRenderer>(format) { TextCarModelPropertyRenderer() }
    bindSingleton<CarColorPropertyRenderer>(format) { TextCarColorPropertyRenderer() }

    // Club
    bindSingleton<ClubNamePropertyRenderer>(format) { TextClubNamePropertyRenderer() }

    // Event
    bindSingleton<EventIdPropertyRenderer>(format) { TextEventIdPropertyRenderer() }
    bindSingleton<EventNamePropertyRenderer>(format) { TextEventNamePropertyRenderer() }
    bindSingleton<EventDatePropertyRenderer>(format) { TextEventDatePropertyRenderer() }
    bindSingleton<EventLifecyclePropertyRenderer>(format) { TextEventLifecyclePropertyRenderer() }
    bindSingleton<EventCrispyFishEventControlFilePropertyRenderer>(format) { TextEventCrispyFishEventControlFilePropertyRenderer() }
    bindSingleton<EventCrispyFishClassDefinitionFilePropertyRenderer>(format) { TextEventCrispyFishClassDefinitionFilePropertyRenderer() }
    bindSingleton<EventMotorsportRegIdPropertyRenderer>(format) { TextEventMotorsportRegIdPropertyRenderer() }

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

    // Policy
    bindSingleton<PolicyIdPropertyRenderer>(format) { TextPolicyIdPropertyRenderer() }
    bindSingleton<PolicyNamePropertyRenderer>(format) { TextPolicyNamePropertyRenderer() }
    bindSingleton<PolicyConePenaltySecondsPropertyRenderer>(format) { TextPolicyConePenaltySecondsPropertyRenderer() }
    bindSingleton<PolicyPaxTimeStylePropertyRenderer>(format) { TextPolicyPaxTimeStylePropertyRenderer() }
    bindSingleton<PolicyFinalScoreStylePropertyRenderer>(format) { TextPolicyFinalScoreStylePropertyRenderer() }

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