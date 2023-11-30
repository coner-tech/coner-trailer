package tech.coner.trailer.app.admin.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Season

class SeasonTableView(
    private val asciiTableFactory: () -> AsciiTable
) : View<List<Season>> {
    override fun render(model: List<Season>): String {
        val at = asciiTableFactory()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("ID", "Name", "Season Points Calculator Config ID", "Take Score Count For Points")
        at.addRule()
        model.forEach {
            at.addRow(it.id, it.name, it.seasonPointsCalculatorConfiguration.id, it.takeScoreCountForPoints)
            at.addRule()
        }
        return at.render()
    }
}