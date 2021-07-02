package org.coner.trailer.di

import org.coner.trailer.render.EventResultsColumn
import org.coner.trailer.render.html.HtmlEventResultsColumnRendererFactory
import org.coner.trailer.render.html.HtmlGroupedEventResultsRenderer
import org.coner.trailer.render.html.HtmlOverallEventResultsRenderer
import org.coner.trailer.render.html.HtmlEventResultsColumn
import org.kodein.di.*

val htmlRenderModule = DI.Module("org.coner.trailer.render.html") {
    bind<HtmlEventResultsColumnRendererFactory>() with singleton { HtmlEventResultsColumnRendererFactory() }
    bind<List<HtmlEventResultsColumn>>() with multiton { columns: List<EventResultsColumn> ->
        instance<HtmlEventResultsColumnRendererFactory>().factory(columns)
    }
    bind<HtmlOverallEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> HtmlOverallEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns)
    ) }
    bind<HtmlGroupedEventResultsRenderer>() with multiton { columns: List<EventResultsColumn> -> HtmlGroupedEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns)
    ) }
}