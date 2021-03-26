package org.coner.trailer.io.constraint

import org.coner.trailer.Policy
import org.coner.trailer.datasource.snoozle.PolicyResource

class PolicyPersistConstraints(
    private val resource: PolicyResource
) : Constraint<Policy>() {

    override fun assess(candidate: Policy) {
        constrain(hasUniqueName(candidate)) { "Name is not unique" }
    }

    fun hasUniqueName(candidate: Policy): Boolean = resource
        .stream()
        .anyMatch { candidate.id != it.id && candidate.name.equals(it.name, ignoreCase = true) }

}
