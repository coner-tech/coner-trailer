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
    ) {
        val handicap: Grouping
            get() = when (val grouping = grouping) {
                is Grouping.Singular -> grouping
                is Grouping.Paired -> grouping.pair.second
            }
    }
}