package org.coner.trailer.di

import org.coner.trailer.render.*
import org.coner.trailer.render.html.*
import org.kodein.di.*

val htmlRenderModule = DI.Module("org.coner.trailer.render.html") {
    val format = Format.HTML
    bind<HtmlEventResultsColumnRendererFactory>() with singleton { HtmlEventResultsColumnRendererFactory() }
    bind<List<HtmlEventResultsColumn>>() with multiton { columns: List<EventResultsColumn> ->
        instance<HtmlEventResultsColumnRendererFactory>().factory(columns)
    }
    bind<OverallEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlOverallEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns)
    ) }
    bind<GroupEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlGroupEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns)
    ) }
    bind<IndividualEventResultsRenderer<String, *>>(Format.HTML) with singleton {
        HtmlIndividualEventResultsRenderer(
            staticColumns = listOf(
                HtmlEventResultsColumn.Name(responsive = false),
                HtmlEventResultsColumn.Signage(responsive = false),
                HtmlEventResultsColumn.CarModel(),
            ),
            dynamicTypeColumnFactory = HtmlIndividualEventResultsColumnRendererFactory()
        )
    }
}