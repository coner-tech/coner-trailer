package org.coner.trailer.io.mapper

import org.coner.trailer.Policy
import org.coner.trailer.datasource.snoozle.entity.PolicyEntity
import org.coner.trailer.eventresults.FinalScoreStyle

class PolicyMapper {

    fun toSnoozle(core: Policy): PolicyEntity {
        return PolicyEntity(
            id = core.id,
            name = core.name,
            conePenaltySeconds = core.conePenaltySeconds,
            finalScoreStyle = core.finalScoreStyle.name
        )
    }

    fun toCore(snoozle: PolicyEntity): Policy {
        return Policy(
            id = snoozle.id,
            name = snoozle.name,
            conePenaltySeconds = snoozle.conePenaltySeconds,
            finalScoreStyle = FinalScoreStyle.valueOf(snoozle.finalScoreStyle)
        )
    }
}
