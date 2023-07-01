package tech.coner.trailer

data class Signage(
    val classing: Classing?,
    val number: String?
) : Comparable<Signage> {

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