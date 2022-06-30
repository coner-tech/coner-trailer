package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Run
import tech.coner.trailer.render.RunRenderer

class TextRunRenderer : RunRenderer {

    override fun render(runs: List<Run>): String {
        val at = AsciiTable().apply {
            renderer.cwc = CWC_LongestLine()
        }
        at.addRule()
        at.addRow("Sequence", "Signage", "Name", "Car Model", "Car Color", "Time", "Penalties", "Rerun")
        at.addRule()
        runs.forEach { run ->
            at.addRow(
                run.sequence,
                run.participant?.signage?.classingNumber ?: "",
                run.participant?.let { renderName(it) } ?: "",
                run.participant?.car?.model ?: "",
                run.participant?.car?.color ?: "",
                render(run.time),
                run.allPenalties?.joinToString { renderPenalty(it) } ?: "",
                if (run.rerun) "RRN" else ""
            )
        }
        at.addRule()
        return at.render()
    }
}