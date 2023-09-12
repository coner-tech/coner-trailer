package tech.coner.trailer.io.model

import tech.coner.trailer.Policy

@JvmInline
value class PolicyCollection(override val items: Collection<Policy>) : ModelCollection<Policy>
