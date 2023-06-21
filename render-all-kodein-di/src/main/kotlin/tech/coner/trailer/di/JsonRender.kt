package tech.coner.trailer.di

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.render.*
import tech.coner.trailer.render.json.*

val jsonRenderModule = DI.Module("tech.coner.trailer.render.json") {
    val format = Format.JSON
    val objectMapper: JsonMapper by lazy {
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
    }
    bindSingleton<ClazzEventResultsRenderer<String, *>>(format) { JsonClazzEventResultsRenderer(objectMapper) }
    bindSingleton<OverallEventResultsRenderer<String, *>>(format) { JsonOverallEventResultsRenderer(objectMapper) }
    bindSingleton<TopTimesEventResultsRenderer<String, *>>(format) { JsonTopTimesEventResultsRenderer(objectMapper) }
    bindSingleton<ComprehensiveEventResultsRenderer<String, *>>(format) { JsonComprehensiveEventResultsRenderer(objectMapper) }
    bindSingleton<IndividualEventResultsRenderer<String, *>>(format) { JsonIndividualEventResultsRenderer(objectMapper) }
}