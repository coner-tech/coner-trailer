package org.coner.trailer

data class Participant(
        val person: Person?,
        val firstName: String,
        val lastName: String,
        val signage: Signage,
        val car: Car,
        val seasonPointsEligible: Boolean
) {

    data class Signage(
            val grouping: Grouping,
            val number: String
    )
}