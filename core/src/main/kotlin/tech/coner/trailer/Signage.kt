package tech.coner.trailer

data class Signage(
    val classing: Classing?,
    val number: String?
) {
    val classingNumber: String? by lazy { "${classing?.abbreviation ?: ""} ${number ?: ""}".trim().ifEmpty { null } }
    val numberClassing: String? by lazy { "${number ?: ""} ${classing?.abbreviation ?: ""}".trim().ifEmpty { null } }
}