package org.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import org.coner.trailer.Event
import org.coner.trailer.eventresults.EventResults
import org.coner.trailer.render.EventResultsRenderer

abstract class TextEventResultsRenderer<ER : EventResults>(
    protected val columns: List<TextEventResultsColumn>
) : EventResultsRenderer<ER, String, () -> String> {

    override fun render(event: Event, results: ER): String {
        return buildString {
            appendHeader(event, results)
            append(partial(event, results).invoke())
            appendLine()
        }
    }

    private fun StringBuilder.appendHeader(event: Event, results: ER) {
        appendLine(event.name)
        appendLine(event.date)
        appendLine(results.type.title)
        appendLine()
    }

    protected fun createAsciiTableWithHeaderRow(results: ER): AsciiTable {
        return AsciiTable().also { at ->
            at.renderer.cwc = CWC_LongestLine()
            at.addRule()
            at.addRow(columns.map { column -> column.header(results.type) })
            at.addRule()
        }
    }

}