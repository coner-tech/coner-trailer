package org.coner.trailer.io.constraint

import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.datasource.snoozle.EventResource
import org.coner.trailer.datasource.snoozle.PolicyResource
import org.coner.trailer.io.mapper.EventMapper

class PolicyPersistConstraints(
    private val resource: PolicyResource,
    private val eventResource: EventResource,
    private val eventMapper: EventMapper
) : Constraint<Policy>() {

    override fun assess(candidate: Policy) {
        constrain(hasUniqueName(candidate)) { "Policy name is not unique" }
        constrain(hasFinalizedEvents(candidate)) { "Policy has finalized events" }
    }

    private fun hasUniqueName(candidate: Policy): Boolean = resource
        .stream { it.id != candidate.id }
        .noneMatch { candidate.name.equals(it.name, ignoreCase = true) }

    private fun hasFinalizedEvents(candidate: Policy): Boolean = eventResource
        .stream()
        .filter { it.policyId == candidate.id }
        .map(eventMapper::toCore)
        .noneMatch { it.lifecycle == Event.Lifecycle.FINAL }

}
