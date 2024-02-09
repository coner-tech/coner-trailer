package tech.coner.trailer.presentation.model

import tech.coner.trailer.Policy
import tech.coner.trailer.presentation.adapter.PolicyModelAdapter
import tech.coner.trailer.presentation.library.model.Model

class PolicyModel(
    private val policy: Policy,
    private val adapter: PolicyModelAdapter
) : Model {
    val id
        get() = adapter.idStringFieldAdapter(policy)
    val name
        get() = adapter.nameStringFieldAdapter(policy)
    val conePenaltySeconds
        get() = adapter.conePenaltySecondsAdapter(policy)
    val paxTimeStyle
        get() = adapter.paxTimeStyleStringFieldAdapter(policy)
    val finalScoreStyle
        get() = adapter.finalScoreStyleStringFieldAdapter(policy)
}