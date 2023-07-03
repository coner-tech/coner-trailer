package tech.coner.trailer.render.text.view.eventresults

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.table.Borders
import com.github.ajalt.mordant.table.SectionBuilder
import com.github.ajalt.mordant.table.TableBuilder
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.Class
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class MordantClassEventResultsViewRenderer(
    private val terminal: Terminal,
    private val signagePropertyRenderer: SignagePropertyRenderer,
    private val participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    private val carModelPropertyRenderer: CarModelPropertyRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer,
    private val participantResultDiffPropertyRenderer: ParticipantResultDiffPropertyRenderer
) : EventResultsViewRenderer<ClassEventResults>,
    PartialRenderer<ClassEventResults, TableBuilder> {

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
                    signagePropertyRenderer(participantResult.participant.signage),
                    participantNamePropertyRenderer(participantResult.participant),
                    carModelPropertyRenderer(participantResult.participant.car),
                    participantResultScoreRenderer(participantResult),
                    participantResultDiffPropertyRenderer(participantResult, ParticipantResult::diffFirst),
                    participantResultDiffPropertyRenderer(participantResult, ParticipantResult::diffPrevious)
                )
            }
        }
    }
}