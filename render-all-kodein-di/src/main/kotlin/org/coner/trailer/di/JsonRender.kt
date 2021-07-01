package org.coner.trailer.di

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.json.JsonGroupedResultsReportRenderer
import org.coner.trailer.render.json.JsonOverallResultsReportRenderer
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton


val jsonRenderModule = DI.Module("org.coner.trailer.render.json") {
    val objectMapper: JsonMapper by lazy {
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
    }
    bind<JsonGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> ->
        JsonGroupedResultsReportRenderer(objectMapper)
    }
    bind<JsonOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> ->
        JsonOverallResultsReportRenderer(objectMapper)
    }
}