package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.render.*
import tech.coner.trailer.render.text.*

val textRenderModule = DI.Module("tech.coner.trailer.render.text") {
    val format = Format.TEXT
    bindSingleton<OverallEventResultsRenderer<String, *>>(format) { TextOverallEventResultsRenderer() }
    bindSingleton<ClazzEventResultsRenderer<String, *>>(format) { MordantClazzEventResultsRenderer(instance()) }
    bindSingleton<TopTimesEventResultsRenderer<String, *>>(format) { TextTopTimesEventResultsRenderer() }
    bindSingleton<ComprehensiveEventResultsRenderer<String, *>>(format) { TextComprehensiveEventResultsRenderer(
        overallRenderer = TextOverallEventResultsRenderer(),
        clazzRenderer = MordantClazzEventResultsRenderer(instance()),
        topTimesRenderer = TextTopTimesEventResultsRenderer()
    ) }
    bind<IndividualEventResultsRenderer<String, *>>(format) with singleton { TextIndividualEventResultsRenderer() }
}