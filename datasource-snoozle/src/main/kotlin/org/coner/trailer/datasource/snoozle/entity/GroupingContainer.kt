package org.coner.trailer.datasource.snoozle.entity

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GroupingContainer(
        val type: Type,
        val singular: String? = null,
        val pair: Pair<String, String>? = null
) {
    enum class Type {
        SINGULAR,
        PAIR
    }
}