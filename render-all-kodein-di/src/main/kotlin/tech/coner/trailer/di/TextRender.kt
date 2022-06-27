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
    bind<GroupEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> TextGroupEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<TextEventResultsColumn>>().invoke(columns)
    ) }
    bind<ComprehensiveEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> TextComprehensiveEventResultsRenderer(
        overallRenderer = factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format).invoke(columns) as TextOverallEventResultsRenderer,
        groupRenderer = factory<List<EventResultsColumn>, GroupEventResultsRenderer<String, *>>(format).invoke(columns) as TextGroupEventResultsRenderer
    ) }
    bind<IndividualEventResultsRenderer<String, *>>(format) with singleton { TextIndividualEventResultsRenderer() }
    bind<TextRunsRenderer>() with singleton { TextRunsRenderer() }
}