package org.coner.trailer

data class Run(
    val sequence: Int,
    val participant: Participant?,
    val cones: Int? = null,
    val didNotFinish: Boolean?,
    val disqualified: Boolean?,
    val rerun: Boolean?,
    val time: Time?
)