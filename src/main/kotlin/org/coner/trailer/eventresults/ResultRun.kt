package org.coner.trailer.eventresults

import java.math.BigDecimal

class ResultRun(
        val time: BigDecimal?,
        val cones: Int,
        val didNotFinish: Boolean = false,
        val disqualified: Boolean = false,
        val rerun: Boolean = false
) {

}