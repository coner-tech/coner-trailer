package org.coner.trailer.cli.view

import org.coner.trailer.Season

class SeasonView : View<Season> {
    override fun render(model: Season): String {
        return """
            ${model.name}
                ID: ${model.name}
                Season Events: // https://github.com/caeos/coner-trailer/issues/27
                Season Points Calculator Configuration
                    ID: ${model.seasonPointsCalculatorConfiguration.id}
                    Name: ${model.seasonPointsCalculatorConfiguration.name}
                Take Score Count For Points: ${model.takeScoreCountForPoints}
        """.trimIndent()
    }
}