package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.render.*
import tech.coner.trailer.render.text.*

val textRenderModule = DI.Module("tech.coner.trailer.render.text") {
    val format = Format.TEXT
    bindSingleton<OverallEventResultsRenderer<String, *>>(format) { TextOverallEventResultsRenderer() }
    bindSingleton<ClazzEventResultsRenderer<String, *>>(format) { TextClazzEventResultsRenderer() }
    bindSingleton<TopTimesEventResultsRenderer<String, *>>(format) { TextTopTimesEventResultsRenderer() }
    bindSingleton<ComprehensiveEventResultsRenderer<String, *>>(format) { TextComprehensiveEventResultsRenderer(
        overallRenderer = instance<OverallEventResultsRenderer<String, *>>(format) as TextOverallEventResultsRenderer,
        clazzRenderer = instance<ClazzEventResultsRenderer<String, *>>(format) as TextClazzEventResultsRenderer,
        topTimesRenderer = instance<TopTimesEventResultsRenderer<String, *>>(format) as TextTopTimesEventResultsRenderer
    ) }
    bind<IndividualEventResultsRenderer<String, *>>(format) with singleton { TextIndividualEventResultsRenderer() }
}