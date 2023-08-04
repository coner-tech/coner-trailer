package tech.coner.trailer.presentation.text.view.eventresults

import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextStyles
import com.github.ajalt.mordant.table.Borders
import com.github.ajalt.mordant.table.SectionBuilder
import com.github.ajalt.mordant.table.TableBuilder
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import tech.coner.trailer.presentation.model.eventresults.ClassEventResultsModel
import tech.coner.trailer.presentation.model.eventresults.ClassParticipantResultsCollectionModel
import tech.coner.trailer.presentation.model.eventresults.EventResultsModel
import tech.coner.trailer.presentation.text.view.TextPartial
import tech.coner.trailer.presentation.text.view.TextView

class MordantClassEventResultsView(
    private val terminal: Terminal,
) : TextView<ClassEventResultsModel>, TextPartial<TableBuilder, ClassEventResultsModel> {

    override fun invoke(model: ClassEventResultsModel): String {
        return buildString {
            appendLine(
                terminal.render(
                    table {
                        tableBorders = Borders.ALL
                        val captionStyle = TextStyles.bold
                        captionTop(
                            text =  """
                                ${captionStyle(model.eventName)}
                                ${captionStyle(model.eventDate)}
                                ${captionStyle(model.eventResultsTypeTitle)}
                                """.trimIndent(),
                            align = TextAlign.LEFT
                        )
                        appendModel(model)
                    }
                )
            )
        }
    }

    override fun TableBuilder.appendModel(model: ClassEventResultsModel) {
        appendHeader(model)
        appendBody(model)
    }

    protected fun TableBuilder.appendHeader(model: EventResultsModel<*>) {
        header {
            row("Pos.", "Signage", "Name", "Car", model.eventResultsTypeScoreColumnHeading, "Diff. 1st", "Diff. Prev.") {
                style(bold = true)
            }
        }
    }

    protected fun TableBuilder.appendBody(model: ClassEventResultsModel) {
        body {
            model.classEventResults.items.forEach { item ->
                appendClass(item)
            }
        }
    }

    protected fun SectionBuilder.appendClass(model: ClassParticipantResultsCollectionModel.Item) {
        row {
            cell(model.clazz.name) {
                columnSpan = 7
                style(bold = true)
            }
        }
        model.participantResults.forEach { participantResult ->
            row {
                cellBorders = Borders.LEFT_RIGHT
                cells(
                    participantResult.position,
                    participantResult.signage,
                    participantResult.name,
                    participantResult.carModel,
                    participantResult.score,
                    participantResult.diffFirst,
                    participantResult.diffPrevious
                )
            }
        }
    }
}