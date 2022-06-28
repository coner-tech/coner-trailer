package tech.coner.trailer

data class Participant(
    val person: Person?,
    val firstName: String?,
    val lastName: String?,
    val signage: Signage?,
    val car: Car?,
    val seasonPointsEligible: Boolean,
    val sponsor: String?
) {
    object Sorts {
        val signage: Comparator<Participant> = compareBy { it.signage }
    }
}

