package tech.coner.trailer

data class Participant(
    val person: Person?,
    val firstName: String?,
    val lastName: String?,
    val number: String?,
    val classing: Classing?,
    val car: Car,
    val seasonPointsEligible: Boolean,
    val sponsor: String?
) {

    val signageClassingNumber: String? by lazy { "${classing?.abbreviation ?: ""} ${number ?: ""}".trim().ifEmpty { null } }
    val signageNumberClassing: String? by lazy { "${number ?: ""} ${classing?.abbreviation ?: ""}".trim().ifEmpty { null } }
}
