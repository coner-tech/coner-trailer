package tech.coner.trailer.render

import tech.coner.trailer.Run
import tech.coner.trailer.eventresults.Score

interface RunRenderer : Renderer {

    fun render(runs: List<Run>): String

    fun renderPenalty(penalty: Run.Penalty): String = when (penalty) {
        Run.Penalty.Disqualified -> disqualified
        Run.Penalty.DidNotFinish -> didNotFinish
        is Run.Penalty.Cone -> "+${penalty.count}"
    }

    fun renderPenalty(penalty: Score.Penalty): String = when (penalty) {
        Score.Penalty.Disqualified -> disqualified
        Score.Penalty.DidNotFinish -> didNotFinish
        is Score.Penalty.Cone -> "+${penalty.count}"
    }

    companion object {
        const val disqualified = "DNF"
        const val didNotFinish = "DNF"
    }
}