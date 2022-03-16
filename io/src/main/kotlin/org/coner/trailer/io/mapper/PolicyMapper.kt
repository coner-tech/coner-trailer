package org.coner.trailer.io.mapper

import org.coner.trailer.Policy
import org.coner.trailer.datasource.snoozle.entity.PolicyEntity
import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import org.coner.trailer.io.service.ClubService

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
            authoritativeRunSource = when (core.authoritativeRunSource) {
                Policy.RunSource.CrispyFish -> PolicyEntity.RunSource(PolicyEntity.RunSource.Type.CRISPY_FISH)
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
            authoritativeRunSource = when (snoozle.authoritativeRunSource.type) {
                PolicyEntity.RunSource.Type.CRISPY_FISH -> Policy.RunSource.CrispyFish
            }
        )
    }
}
