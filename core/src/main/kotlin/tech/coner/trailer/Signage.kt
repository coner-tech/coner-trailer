package tech.coner.trailer

data class Signage(
    val classing: Classing?,
    val number: String?
) : Comparable<Signage> {
    val classingNumber: String? by lazy { "${classing?.abbreviation ?: ""} ${number ?: ""}".trim().ifEmpty { null } }
    val numberClassing: String? by lazy { "${number ?: ""} ${classing?.abbreviation ?: ""}".trim().ifEmpty { null } }

    override fun compareTo(other: Signage): Int {
        return comparator.compare(this, other)
    }

    companion object {
        private val comparator: Comparator<Signage> by lazy {
            compareBy<Signage> { it.classing }
                .thenBy { it.number }
        }
    }
}