package org.coner.trailer

data class Participant(
        val person: Person,
        val grouping: Grouping,
        val car: Car,
        val seasonPointsEligible: Boolean
)