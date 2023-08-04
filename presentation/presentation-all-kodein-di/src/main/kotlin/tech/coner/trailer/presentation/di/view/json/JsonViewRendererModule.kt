package tech.coner.trailer.presentation.di.view.json

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.presentation.json.eventresults.*

val jsonViewRenderModule = DI.Module("tech.coner.trailer.json") {
    val objectMapper: JsonMapper by lazy {
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
    }
    bindSingleton { JsonClassEventResultsViewRenderer(objectMapper) }
    bindSingleton { JsonOverallEventResultsViewRenderer(objectMapper) }
    bindSingleton { JsonTopTimesEventResultsViewRenderer(objectMapper) }
    bindSingleton { JsonComprehensiveEventResultsViewRenderer(objectMapper) }
    bindSingleton { JsonIndividualEventResultsViewRenderer(objectMapper) }
}