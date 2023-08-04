package tech.coner.trailer.io.model

import tech.coner.trailer.Policy

data class PolicyCollection(override val items: Collection<Policy>) : ModelCollection<Policy>
