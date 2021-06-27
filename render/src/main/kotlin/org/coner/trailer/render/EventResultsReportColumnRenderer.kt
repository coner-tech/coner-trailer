package org.coner.trailer.render

interface EventResultsReportColumnRenderer<HEADER, DATA> : Renderer {

    val header: HEADER

    val data: DATA
}