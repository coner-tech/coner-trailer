package org.coner.trailer.render

interface EventResultsColumnRendererFactory<
        CR : EventResultsColumnRenderer<*, *>> {

    fun factory(columns: List<EventResultsColumn>): List<CR>
}