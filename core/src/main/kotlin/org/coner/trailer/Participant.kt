package org.coner.trailer

data class Participant(
    val person: Person?,
    val firstName: String?,
    val lastName: String?,
    val signage: Signage?,
    val car: Car,
    val seasonPointsEligible: Boolean,
    val sponsor: String?
) {

    data class Signage(
        val grouping: Grouping?,
        val number: String?
    ) {
        val category: Grouping?
            get() = when (val grouping = grouping) {
                is Grouping.Paired -> grouping.pair.first
                else -> null
            }
        val handicap: Grouping?
            get() = when (val grouping = grouping) {
                is Grouping.Singular -> grouping
                is Grouping.Paired -> grouping.pair.second
                else -> null
            }

        fun toAbbreviatedString() = "${grouping?.abbreviation} $number".trim()
    }
}