package org.coner.trailer.render

interface EventResultsColumnRenderer<HEADER, DATA> : Renderer {

    val header: HEADER

    val data: DATA
}