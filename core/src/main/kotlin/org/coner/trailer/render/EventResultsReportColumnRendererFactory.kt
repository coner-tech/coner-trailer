package org.coner.trailer.render

interface EventResultsReportColumnRendererFactory<
        CR : EventResultsReportColumnRenderer<*, *>> {

    fun factory(columns: List<EventResultsReportColumn>): List<CR>
}