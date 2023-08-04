package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Policy
import tech.coner.trailer.io.model.PolicyCollection
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel

class PolicyIdStringFieldAdapter : StringFieldAdapter<Policy> {
    override operator fun invoke(model: Policy): String {
        return model.id.toString()
    }
}

class PolicyNameStringFieldAdapter : StringFieldAdapter<Policy> {
    override operator fun invoke(model: Policy): String {
        return model.name
    }
}

class PolicyConePenaltySecondsStringFieldAdapter : StringFieldAdapter<Policy> {
    override operator fun invoke(model: Policy): String {
        return model.conePenaltySeconds.toString()
    }
}

class PolicyPaxTimeStyleStringFieldAdapter : StringFieldAdapter<Policy> {
    override operator fun invoke(model: Policy): String {
        return model.paxTimeStyle.name.lowercase()
    }
}

class PolicyFinalScoreStyleStringFieldAdapter : StringFieldAdapter<Policy> {
    override operator fun invoke(model: Policy): String {
        return model.finalScoreStyle.name.lowercase()
    }
}

class PolicyModelAdapter(
    val idStringFieldAdapter: PolicyIdStringFieldAdapter,
    val nameStringFieldAdapter: PolicyNameStringFieldAdapter,
    val conePenaltySecondsAdapter: PolicyConePenaltySecondsStringFieldAdapter,
    val paxTimeStyleStringFieldAdapter: PolicyPaxTimeStyleStringFieldAdapter,
    val finalScoreStyleStringFieldAdapter: PolicyFinalScoreStyleStringFieldAdapter
) : Adapter<Policy, PolicyModel> {
    override fun invoke(model: Policy): PolicyModel {
        return PolicyModel(
            policy = model,
            adapter = this
        )
    }
}

class PolicyCollectionModelAdapter(
    private val adapter: PolicyModelAdapter
) : Adapter<PolicyCollection, PolicyCollectionModel> {
    override fun invoke(model: PolicyCollection): PolicyCollectionModel {
        return PolicyCollectionModel(
            items = model.items.map(adapter::invoke)
        )
    }
}