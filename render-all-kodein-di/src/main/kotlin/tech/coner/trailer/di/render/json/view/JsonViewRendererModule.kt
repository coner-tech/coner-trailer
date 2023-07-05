package tech.coner.trailer.di.render.json.view

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import tech.coner.trailer.Policy
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.eventresults.*
import tech.coner.trailer.render.json.eventresults.*
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

val jsonViewRenderModule = DI.Module("tech.coner.trailer.render.json.view") {
    val format = Format.JSON
    val objectMapper: JsonMapper by lazy {
        jacksonMapperBuilder()
            .addModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build()
    }
    bindSingleton<EventResultsViewRenderer<ClassEventResults>>(format) {
        JsonClassEventResultsViewRenderer(objectMapper)
    }
    bindSingleton<EventResultsViewRenderer<OverallEventResults>>(format) {
        JsonOverallEventResultsViewRenderer(objectMapper)
    }
    bindSingleton<EventResultsViewRenderer<TopTimesEventResults>>(format) {
        JsonTopTimesEventResultsViewRenderer(objectMapper)
    }
    bindSingleton<EventResultsViewRenderer<ComprehensiveEventResults>>(format) {
        JsonComprehensiveEventResultsViewRenderer(objectMapper)
    }
    bindSingleton<EventResultsViewRenderer<IndividualEventResults>>(format) {
        JsonIndividualEventResultsViewRenderer(objectMapper)
    }
}