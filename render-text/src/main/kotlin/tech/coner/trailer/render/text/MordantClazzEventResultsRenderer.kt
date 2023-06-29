package tech.coner.trailer.render.text

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.table.Borders
import com.github.ajalt.mordant.table.SectionBuilder
import com.github.ajalt.mordant.table.TableBuilder
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.render.ClazzEventResultsRenderer

class MordantClazzEventResultsRenderer(
    private val terminal: Terminal
) : ClazzEventResultsRenderer<String, TableBuilder.() -> Unit> {

    override fun render(eventContext: EventContext, results: ClazzEventResults): String {
        return buildString {
            appendLine(
                terminal.render(
                    table {
                        tableBorders = Borders.ALL
                        captionTop(
                            text = buildString {
                                appendLine(eventContext.event.name)
                                appendLine(eventContext.event.date)
                                appendLine(results.type.title)
                            },
                            align = TextAlign.LEFT
                        )
                        partial(eventContext, results).invoke(this)
                    }
                )
            )
        }
    }

    override fun partial(eventContext: EventContext, results: ClazzEventResults): TableBuilder.() -> Unit = {
        appendHeader(results)
        appendBody(results)
    }

    protected fun TableBuilder.appendHeader(results: EventResults) {
        header {
            row("Pos.", "Signage", "Name", "Car", results.type.scoreColumnHeading, "Diff. 1st", "Diff. Prev.")
        }
    }

    protected fun TableBuilder.appendBody(results: ClazzEventResults) {
        body {
            results.groupParticipantResults.forEach { (group, participantResults) ->
                appendGroup(group, participantResults)

            }
        }
    }

    protected fun SectionBuilder.appendGroup(group: Class, participantResults: List<ParticipantResult>) {
        row {
            cell(group.name) { columnSpan = 7 }
        }
        participantResults.forEachIndexed { index, participantResult ->
            row {
                cellBorders = Borders.LEFT_RIGHT
                cells(
                    "${participantResult.position}",
                    participantResult.participant.signage?.classingNumber ?: "",
                    renderName(participantResult.participant),
                    participantResult.participant.car?.model ?: "",
                    renderScoreColumnValue(participantResult),
                    renderDiffColumnValue(participantResult, participantResult.diffFirst),
                    renderDiffColumnValue(participantResult, participantResult.diffPrevious)
                )
            }
        }
    }

    private fun renderDiffColumnValue(participantResult: ParticipantResult, diff: Time?) = when (participantResult.position) {
        1 -> ""
        else -> render(diff)
    }
}