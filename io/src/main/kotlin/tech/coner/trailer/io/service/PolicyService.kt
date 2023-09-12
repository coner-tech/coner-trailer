package tech.coner.trailer.io.service

import tech.coner.trailer.Policy
import tech.coner.trailer.datasource.snoozle.PolicyResource
import tech.coner.trailer.datasource.snoozle.entity.PolicyEntity
import tech.coner.trailer.io.constraint.PolicyDeleteConstraints
import tech.coner.trailer.io.constraint.PolicyPersistConstraints
import tech.coner.trailer.io.mapper.PolicyMapper
import tech.coner.trailer.io.model.PolicyCollection
import java.util.*

class PolicyService(
    private val persistConstraints: PolicyPersistConstraints,
    private val deleteConstraints: PolicyDeleteConstraints,
    private val resource: PolicyResource,
    private val mapper: PolicyMapper
) {
    fun create(create: Policy) {
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): Policy {
        val key = PolicyEntity.Key(id = id)
        return mapper.toCore(resource.read(key))
    }

    fun findByName(name: String): Policy {
        val entity = resource.stream()
            .filter { it.name.equals(name, ignoreCase = true) }
            .findFirst()
            .get()
        return mapper.toCore(entity)
    }

    fun list(): PolicyCollection {
        return resource.stream()
            .sorted(compareBy(PolicyEntity::name))
            .map(mapper::toCore)
            .toList()
            .let { PolicyCollection(it) }
    }

    fun update(update: Policy) {
        persistConstraints.assess(update)
        resource.update(mapper.toSnoozle(update))
    }

    fun delete(delete: Policy) {
        deleteConstraints.assess(delete)
        resource.delete(mapper.toSnoozle(delete))
    }
}