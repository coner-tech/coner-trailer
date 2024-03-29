package tech.coner.trailer.io.mapper

import tech.coner.trailer.Policy
import tech.coner.trailer.SignageStyle
import tech.coner.trailer.datasource.snoozle.entity.PolicyEntity
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.service.ClubService

class PolicyMapper(
    private val clubService: ClubService
) {

    fun toSnoozle(core: Policy): PolicyEntity {
        return PolicyEntity(
            id = core.id,
            name = core.name,
            conePenaltySeconds = core.conePenaltySeconds,
            paxTimeStyle = core.paxTimeStyle.name,
            finalScoreStyle = core.finalScoreStyle.name,
            authoritativeRunSource = when (core.requireAuthoritativeRunDataSource()) {
                Policy.DataSource.CrispyFish -> PolicyEntity.DataSource(PolicyEntity.DataSource.Type.CRISPY_FISH)
            }
        )
    }

    fun toCore(snoozle: PolicyEntity): Policy {
        return Policy(
            id = snoozle.id,
            club = clubService.get(),
            name = snoozle.name,
            conePenaltySeconds = snoozle.conePenaltySeconds,
            paxTimeStyle = PaxTimeStyle.valueOf(snoozle.paxTimeStyle),
            finalScoreStyle = FinalScoreStyle.valueOf(snoozle.finalScoreStyle),
            authoritativeParticipantDataSource = when (snoozle.authoritativeParticipantSource.type) {
                PolicyEntity.DataSource.Type.CRISPY_FISH -> Policy.DataSource.CrispyFish
            },
            authoritativeRunDataSource = when (snoozle.authoritativeRunSource.type) {
                PolicyEntity.DataSource.Type.CRISPY_FISH -> Policy.DataSource.CrispyFish
            },
            topTimesEventResultsMethod = StandardEventResultsTypes.pax, //  TODO: support configuring as raw or pax
            signageStyle = SignageStyle.CLASSING_NUMBER, // TODO: support configuring
        )
    }
}
