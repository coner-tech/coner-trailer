package org.coner.trailer.di

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.json.JsonGroupedResultsReportRenderer
import org.coner.trailer.render.json.JsonOverallResultsReportRenderer
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton


val jsonRenderModule = DI.Module("org.coner.trailer.render.json") {
    val objectMapper by lazy { jacksonObjectMapper() }
    bind<JsonGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> ->
        JsonGroupedResultsReportRenderer(objectMapper)
    }
    bind<JsonOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> ->
        JsonOverallResultsReportRenderer(objectMapper)
    }
}