package tech.coner.trailer.render.text.eventresults

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.table.Borders
import com.github.ajalt.mordant.table.SectionBuilder
import com.github.ajalt.mordant.table.TableBuilder
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.Class
import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.eventresults.ClassEventResultsRenderer
import tech.coner.trailer.render.internal.*

class MordantClassEventResultsRenderer(
    private val terminal: Terminal,
    private val signageRenderer: SignageRenderer,
    private val participantNameRenderer: ParticipantNameRenderer,
    private val carModelRenderer: CarModelRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer,
    private val participantResultDiffRenderer: ParticipantResultDiffRenderer
) : ClassEventResultsRenderer, PartialRenderer<ClassEventResults, TableBuilder> {

    override fun render(model: ClassEventResults): String {
        return buildString {
            appendLine(
                terminal.render(
                    table {
                        tableBorders = Borders.ALL
                        val captionStyle = TextStyles.bold
                        captionTop(
                            text =  """
                                ${captionStyle(model.eventContext.event.name)}
                                ${captionStyle(model.eventContext.event.date.toString())}
                                ${captionStyle(model.type.title)}
                                """.trimIndent(),
                            align = TextAlign.LEFT
                        )
                        renderPartial(model)
                    }
                )
            )
        }
    }

    override fun TableBuilder.renderPartial(model: ClassEventResults) {
        appendHeader(model)
        appendBody(model)
    }

    protected fun TableBuilder.appendHeader(results: EventResults) {
        header {
            row("Pos.", "Signage", "Name", "Car", results.type.scoreColumnHeading, "Diff. 1st", "Diff. Prev.") {
                style(bold = true)
            }
        }
    }

    protected fun TableBuilder.appendBody(results: ClassEventResults) {
        body {
            results.groupParticipantResults.forEach { (group, participantResults) ->
                appendGroup(group, participantResults)
            }
        }
    }

    protected fun SectionBuilder.appendGroup(group: Class, participantResults: List<ParticipantResult>) {
        row {
            cell(group.name) {
                columnSpan = 7
                style(bold = true)
            }
        }
        participantResults.forEachIndexed { index, participantResult ->
            row {
                cellBorders = Borders.LEFT_RIGHT
                cells(
                    "${participantResult.position}",
                    signageRenderer(participantResult.participant.signage),
                    participantNameRenderer(participantResult.participant),
                    carModelRenderer(participantResult.participant.car),
                    participantResultScoreRenderer(participantResult),
                    participantResultDiffRenderer(participantResult, ParticipantResult::diffFirst),
                    participantResultDiffRenderer(participantResult, ParticipantResult::diffPrevious)
                )
            }
        }
    }
}