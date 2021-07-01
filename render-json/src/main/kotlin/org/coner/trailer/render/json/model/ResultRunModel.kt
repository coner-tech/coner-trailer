package org.coner.trailer.render.json.model

import org.coner.trailer.eventresults.ResultRun
import org.coner.trailer.eventresults.Score

class ResultRunModel(val run: RunModel, val score: Score) {

    constructor(resultRun: ResultRun) : this(
        run = RunModel(resultRun.run),
        score = resultRun.score
    )
}