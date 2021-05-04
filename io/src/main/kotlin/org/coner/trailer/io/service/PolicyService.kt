package org.coner.trailer.io.service

import org.coner.trailer.Policy
import org.coner.trailer.datasource.snoozle.PolicyResource
import org.coner.trailer.datasource.snoozle.entity.PolicyEntity
import org.coner.trailer.io.constraint.PolicyDeleteConstraints
import org.coner.trailer.io.constraint.PolicyPersistConstraints
import org.coner.trailer.io.mapper.PolicyMapper
import java.util.*
import kotlin.streams.toList

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

    fun list(): List<Policy> {
        return resource.stream()
            .sorted(compareBy(PolicyEntity::name))
            .map(mapper::toCore)
            .toList()
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