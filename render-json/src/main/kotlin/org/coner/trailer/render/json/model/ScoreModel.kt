package org.coner.trailer.render.json.model

import org.coner.trailer.eventresults.Score

class ScoreModel(
    val time: String,
    val penalty: Score.Penalty?
) {

    constructor(score: Score) : this(
        time = score.value.toString(),
        penalty = score.penalty
    )
}