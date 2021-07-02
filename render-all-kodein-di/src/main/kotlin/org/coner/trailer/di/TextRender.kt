package org.coner.trailer.di

import org.coner.trailer.render.EventResultsColumn
import org.coner.trailer.render.text.TextEventResultsColumnRendererFactory
import org.coner.trailer.render.text.TextGroupedEventResultsRenderer
import org.coner.trailer.render.text.TextOverallEventResultsRenderer
import org.coner.trailer.render.text.TextEventResultsColumn
import org.kodein.di.*

val textRenderModule = DI.Module("org.coner.trailer.render.text") {
    bind<TextEventResultsColumnRendererFactory>() with singleton { TextEventResultsColumnRendererFactory() }
    bind<List<TextEventResultsColumn>>() with multiton { columns: List<EventResultsColumn> ->
        instance<TextEventResultsColumnRendererFactory>().factory(columns)
    }
    bind<TextOverallEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> TextOverallEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<TextEventResultsColumn>>().invoke(columns)
    ) }
    bind<TextGroupedEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> TextGroupedEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<TextEventResultsColumn>>().invoke(columns)
    ) }
}