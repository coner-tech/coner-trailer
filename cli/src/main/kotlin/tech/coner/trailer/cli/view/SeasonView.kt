package tech.coner.trailer.cli.view

import tech.coner.trailer.Season

class SeasonView : View<Season> {
    override fun render(model: Season): String {
        return """
            ${model.name}
                ID: ${model.id}
                Season Events: // https://github.com/caeos/coner-trailer/issues/27
                Season Points Calculator Configuration
                    ID: ${model.seasonPointsCalculatorConfiguration.id}
                    Name: ${model.seasonPointsCalculatorConfiguration.name}
                Take Score Count For Points: ${model.takeScoreCountForPoints}
        """.trimIndent()
    }
}