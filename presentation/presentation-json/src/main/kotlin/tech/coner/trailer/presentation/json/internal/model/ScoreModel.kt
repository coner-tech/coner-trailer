package tech.coner.trailer.presentation.json.internal.model

import tech.coner.trailer.eventresults.Score

class ScoreModel(
    val time: String,
    val penalty: Score.Penalty?
) {

    constructor(score: Score) : this(
        time = score.value.toString(),
        penalty = score.penalty
    )
}