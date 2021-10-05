package org.coner.trailer.di

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.coner.trailer.render.*
import org.coner.trailer.render.json.JsonGroupEventResultsRenderer
import org.coner.trailer.render.json.JsonIndividualEventResultsRenderer
import org.coner.trailer.render.json.JsonOverallEventResultsRenderer
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.singleton

val jsonRenderModule = DI.Module("org.coner.trailer.render.json") {
    val format = Format.JSON
    val objectMapper: JsonMapper by lazy {
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
    }
    bind<GroupEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> ->
        JsonGroupEventResultsRenderer(objectMapper)
    }
    bind<OverallEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> ->
        JsonOverallEventResultsRenderer(objectMapper)
    }
    bind<IndividualEventResultsRenderer<String, *>>(format) with singleton {
        JsonIndividualEventResultsRenderer(objectMapper)
    }
}