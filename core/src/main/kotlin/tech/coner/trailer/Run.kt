package tech.coner.trailer

data class Run(
    val sequence: Int,
    val participant: Participant? = null,
    val cones: Int = 0,
    val didNotFinish: Boolean = false,
    val disqualified: Boolean = false,
    val rerun: Boolean = false,
    val time: Time? = null
) {
    init {
        require(cones >= 0) { "Cones must be greater than or equal to 0 but was $cones" }
    }
}