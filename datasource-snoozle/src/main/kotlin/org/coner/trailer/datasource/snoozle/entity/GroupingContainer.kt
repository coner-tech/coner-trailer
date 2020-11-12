package org.coner.trailer.datasource.snoozle.entity

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GroupingContainer(
        val type: Type,
        val singular: String?,
        val pair: Pair<String, String>?
) {
    enum class Type {
        SINGULAR,
        PAIR
    }
}