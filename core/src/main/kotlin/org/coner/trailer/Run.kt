package org.coner.trailer

data class Run(
    val sequence: Int,
    val participant: Participant? = null,
    val cones: Int? = null,
    val didNotFinish: Boolean? = null,
    val disqualified: Boolean? = null,
    val rerun: Boolean? = null,
    val time: Time? = null
)