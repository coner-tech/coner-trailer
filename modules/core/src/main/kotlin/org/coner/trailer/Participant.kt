package org.coner.trailer

data class Participant(
        val person: Person?,
        val firstName: String,
        val lastName: String,
        val grouping: Grouping,
        val number: String,
        val car: Car,
        val seasonPointsEligible: Boolean
)