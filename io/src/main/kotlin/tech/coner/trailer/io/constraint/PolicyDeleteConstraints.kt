package tech.coner.trailer.io.constraint

import tech.coner.trailer.Policy
import tech.coner.trailer.datasource.snoozle.EventResource

class PolicyDeleteConstraints(
    private val eventResource: EventResource
) : Constraint<Policy>() {

    override fun assess(candidate: Policy) {
        constrain(
            eventResource.stream().noneMatch { it.policyId == candidate.id }
        ) { "Policy is referenced by an event" }
    }
}