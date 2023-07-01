package tech.coner.trailer.di

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.kodein.di.DI
import org.kodein.di.bindFactory
import tech.coner.trailer.Policy
import tech.coner.trailer.render.eventresults.*
import tech.coner.trailer.render.json.eventresults.*

val jsonRenderModule = DI.Module("tech.coner.trailer.render.json") {
    val format = Format.JSON
    val objectMapper: JsonMapper by lazy {
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
    }
    bindFactory<Policy, ClassEventResultsRenderer>(format) { JsonClassEventResultsRenderer(objectMapper) }
    bindFactory<Policy, OverallEventResultsRenderer>(format) { JsonOverallEventResultsRenderer(objectMapper) }
    bindFactory<Policy, TopTimesEventResultsRenderer>(format) { JsonTopTimesEventResultsRenderer(objectMapper) }
    bindFactory<Policy, ComprehensiveEventResultsRenderer>(format) { JsonComprehensiveEventResultsRenderer(objectMapper) }
    bindFactory<Policy, IndividualEventResultsRenderer>(format) { JsonIndividualEventResultsRenderer(objectMapper) }
}