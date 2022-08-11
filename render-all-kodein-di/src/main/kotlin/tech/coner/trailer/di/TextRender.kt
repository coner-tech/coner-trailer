package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.render.*
import tech.coner.trailer.render.text.*

val textRenderModule = DI.Module("tech.coner.trailer.render.text") {
    val format = Format.TEXT
    bind<TextEventResultsColumnRendererFactory>() with singleton { TextEventResultsColumnRendererFactory() }
    bind<List<TextEventResultsColumn>>() with multiton { columns: List<EventResultsColumn> ->
        instance<TextEventResultsColumnRendererFactory>().factory(columns)
    }
    bind<OverallEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> TextOverallEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<TextEventResultsColumn>>().invoke(columns)
    ) }
    bind<ClazzEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> TextClazzEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<TextEventResultsColumn>>().invoke(columns)
    ) }
    bind<TopTimesEventResultsRenderer<String, *>>(format) with singleton { TextTopTimesEventResultsRenderer() }
    bind<ComprehensiveEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> TextComprehensiveEventResultsRenderer(
        overallRenderer = factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format).invoke(columns) as TextOverallEventResultsRenderer,
        clazzRenderer = factory<List<EventResultsColumn>, ClazzEventResultsRenderer<String, *>>(format).invoke(columns) as TextClazzEventResultsRenderer,
        topTimesRenderer = instance<TopTimesEventResultsRenderer<String, *>>(format) as TextTopTimesEventResultsRenderer
    ) }
    bind<IndividualEventResultsRenderer<String, *>>(format) with singleton { TextIndividualEventResultsRenderer() }
}