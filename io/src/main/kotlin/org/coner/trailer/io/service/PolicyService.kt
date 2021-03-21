package org.coner.trailer.io.service

import org.coner.trailer.Policy
import org.coner.trailer.datasource.snoozle.PolicyResource
import org.coner.trailer.datasource.snoozle.entity.PolicyEntity
import org.coner.trailer.io.mapper.PolicyMapper
import java.util.*

class PolicyService(
    private val resource: PolicyResource,
    private val mapper: PolicyMapper
) {
    fun findById(id: UUID): Policy {
        val key = PolicyEntity.Key(id = id)
        return mapper.toCore(resource.read(key))
    }

    fun findByName(name: String): Policy {
        val entity = resource.stream()
            .filter { it.name == name }
            .findFirst()
            .get()
        return mapper.toCore(entity)
    }
}