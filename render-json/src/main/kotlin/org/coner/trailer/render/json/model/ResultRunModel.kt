package org.coner.trailer.render.json.model

import org.coner.trailer.eventresults.ResultRun

class ResultRunModel(val run: RunModel, val score: ScoreModel) {

    constructor(resultRun: ResultRun) : this(
        run = RunModel(resultRun.run),
        score = ScoreModel(resultRun.score)
    )
}