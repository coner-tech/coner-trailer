package tech.coner.trailer.render.text.view

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Run
import tech.coner.trailer.render.view.RunsViewRenderer
import tech.coner.trailer.render.property.*

class TextRunsViewRenderer(
    private val runSequencePropertyRenderer: RunSequencePropertyRenderer,
    private val signagePropertyRenderer: SignagePropertyRenderer,
    private val carModelPropertyRenderer: CarModelPropertyRenderer,
    private val carColorPropertyRenderer: CarColorPropertyRenderer,
    private val nullableParticipantNamePropertyRenderer: NullableParticipantNamePropertyRenderer,
    private val nullableTimePropertyRenderer: NullableTimePropertyRenderer,
    private val penaltiesPropertyRenderer: PenaltiesPropertyRenderer,
    private val runRerunPropertyRenderer: RunRerunPropertyRenderer
) : RunsViewRenderer {

    override fun render(model: Collection<Run>): String {
        val at = AsciiTable().apply {
            renderer.cwc = CWC_LongestLine()
        }
        at.addRule()
        at.addRow("Sequence", "Signage", "Name", "Car Model", "Car Color", "Time", "Penalties", "Rerun")
        at.addRule()
        model.forEach { run ->
            at.addRow(
                runSequencePropertyRenderer(run),
                signagePropertyRenderer(run.participant?.signage, ),
                nullableParticipantNamePropertyRenderer(run.participant),
                carModelPropertyRenderer(run.participant?.car),
                carColorPropertyRenderer(run.participant?.car),
                nullableTimePropertyRenderer(run.time),
                penaltiesPropertyRenderer(run.allPenalties),
                runRerunPropertyRenderer(run)
            )
        }
        at.addRule()
        return at.render()
    }
}