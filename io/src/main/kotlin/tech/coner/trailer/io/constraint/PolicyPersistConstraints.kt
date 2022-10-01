package tech.coner.trailer.io.constraint

import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.PolicyResource
import tech.coner.trailer.datasource.snoozle.entity.EventEntity
import tech.coner.trailer.io.mapper.EventMapper
import java.util.*

class PolicyPersistConstraints(
    private val resource: PolicyResource,
    private val eventResource: EventResource,
    private val eventMapper: EventMapper
) : Constraint<Policy>() {

    override fun assess(candidate: Policy) {
        hasUniqueName(candidate.id to candidate.name).getOrThrow()
        hasFinalizedEvents(candidate.id).getOrThrow()
    }

    val hasUniqueName = constraint<Pair<UUID, String>>(
        { (candidateId, candidateName) ->
            resource.stream { it.id != candidateId }
                .noneMatch { candidateName.equals(it.name, ignoreCase = true) }
        },
        { "Name is not unique" }
    )

    val hasFinalizedEvents = constraint<UUID>(
        { candidateId ->
            eventResource
                .stream()
                .filter { it.policyId == candidateId }
                .map(eventMapper::toCore)
                .noneMatch { it.lifecycle == Event.Lifecycle.FINAL }
        },
        { "Policy has finalized events" }
    )
}
