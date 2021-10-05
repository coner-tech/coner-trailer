package org.coner.trailer.di

import org.coner.trailer.render.*
import org.coner.trailer.render.text.*
import org.kodein.di.*

val textRenderModule = DI.Module("org.coner.trailer.render.text") {
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
    bind<IndividualEventResultsRenderer<String, *>>(format) with singleton { TextIndividualEventResultsRenderer() }
}