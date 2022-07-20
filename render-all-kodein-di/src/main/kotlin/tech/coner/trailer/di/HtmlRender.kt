package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.render.*
import tech.coner.trailer.render.html.*

val htmlRenderModule = DI.Module("tech.coner.trailer.render.html") {
    val format = Format.HTML
    bind<HtmlEventResultsColumnRendererFactory>() with singleton { HtmlEventResultsColumnRendererFactory() }
    bind<List<HtmlEventResultsColumn>>() with multiton { columns: List<EventResultsColumn> ->
        instance<HtmlEventResultsColumnRendererFactory>().factory(columns)
    }
    bind<OverallEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlOverallEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns)
    ) }
    bind<ClazzEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlClazzEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns)
    ) }
    bind<TopTimesEventResultsRenderer<String, *>>(format) with singleton { HtmlTopTimesEventResultsRenderer() }
    bind<ComprehensiveEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlComprehensiveEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns),
        overallRenderer = factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format)(columns) as HtmlOverallEventResultsRenderer,
        clazzRenderer = factory<List<EventResultsColumn>, ClazzEventResultsRenderer<String, *>>(format)(columns) as HtmlClazzEventResultsRenderer,
        topTimesRenderer = instance<TopTimesEventResultsRenderer<String, *>>(format) as HtmlTopTimesEventResultsRenderer
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