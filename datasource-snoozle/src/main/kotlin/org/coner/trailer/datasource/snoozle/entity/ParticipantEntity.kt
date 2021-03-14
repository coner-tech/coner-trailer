package org.coner.trailer.datasource.snoozle.entity

class ParticipantEntity {

    data class Signage(
        val grouping: GroupingContainer,
        val number: String
    )
}