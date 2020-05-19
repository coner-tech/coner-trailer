package org.coner.trailer.eventresults

import org.coner.trailer.Time

class ResultRun(
        val time: Time?, // scratch time
        val cones: Int? = null,
        val didNotFinish: Boolean = false,
        val disqualified: Boolean = false,
        val rerun: Boolean = false
) {

}