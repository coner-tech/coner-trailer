package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Run
import tech.coner.trailer.render.RunsRenderer
import tech.coner.trailer.render.internal.*

class TextRunsRenderer(
    private val runSequenceRenderer: RunSequenceRenderer,
    private val signageRenderer: SignageRenderer,
    private val carModelRenderer: CarModelRenderer,
    private val carColorRenderer: CarColorRenderer,
    private val nullableParticipantNameRenderer: NullableParticipantNameRenderer,
    private val nullableTimeRenderer: NullableTimeRenderer,
    private val penaltiesRenderer: PenaltiesRenderer,
    private val runRerunRenderer: RunRerunRenderer
) : RunsRenderer {

    override fun render(model: Collection<Run>): String {
        val at = AsciiTable().apply {
            renderer.cwc = CWC_LongestLine()
        }
        at.addRule()
        at.addRow("Sequence", "Signage", "Name", "Car Model", "Car Color", "Time", "Penalties", "Rerun")
        at.addRule()
        model.forEach { run ->
            at.addRow(
                runSequenceRenderer(run),
                signageRenderer(run.participant?.signage, ),
                nullableParticipantNameRenderer(run.participant),
                carModelRenderer(run.participant?.car),
                carColorRenderer(run.participant?.car),
                nullableTimeRenderer(run.time),
                penaltiesRenderer(run.allPenalties),
                runRerunRenderer(run)
            )
        }
        at.addRule()
        return at.render()
    }
}