package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.render.*
import tech.coner.trailer.render.html.*
import tech.coner.trailer.render.json.JsonClazzEventResultsRenderer
import tech.coner.trailer.render.json.JsonOverallEventResultsRenderer
import tech.coner.trailer.render.json.JsonTopTimesEventResultsRenderer

val htmlRenderModule = DI.Module("tech.coner.trailer.render.html") {
    val format = Format.HTML
    bind<HtmlEventResultsColumnRendererFactory>() with singleton { HtmlEventResultsColumnRendererFactory() }
    bind<List<HtmlEventResultsColumn>>() with multiton { columns: List<EventResultsColumn> ->
        instance<HtmlEventResultsColumnRendererFactory>().factory(columns)
    }
    bind<OverallEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlOverallEventResultsRenderer(
        jsonRenderer = factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(Format.JSON)
            .invoke(emptyList()) as JsonOverallEventResultsRenderer
    ) }
    bind<ClazzEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlClazzEventResultsRenderer(
        jsonRenderer = factory<List<EventResultsColumn>, ClazzEventResultsRenderer<String, *>>(Format.JSON)
            .invoke(emptyList()) as JsonClazzEventResultsRenderer
    ) }
    bind<TopTimesEventResultsRenderer<String, *>>(format) with singleton { HtmlTopTimesEventResultsRenderer(
        jsonRenderer = instance<TopTimesEventResultsRenderer<String, *>>(Format.JSON) as JsonTopTimesEventResultsRenderer
    ) }
    bind<ComprehensiveEventResultsRenderer<String, *>>(format) with multiton { columns: List<EventResultsColumn> -> HtmlComprehensiveEventResultsRenderer(
        columns = factory<List<EventResultsColumn>, List<HtmlEventResultsColumn>>().invoke(columns),
        overallRenderer = factory<List<EventResultsColumn>, OverallEventResultsRenderer<String, *>>(format)(columns) as HtmlOverallEventResultsRenderer,
        clazzRenderer = factory<List<EventResultsColumn>, ClazzEventResultsRenderer<String, *>>(format)(columns) as HtmlClazzEventResultsRenderer,
        topTimesRenderer = instance<TopTimesEventResultsRenderer<String, *>>(format) as HtmlTopTimesEventResultsRenderer,
        individualRenderer = instance<IndividualEventResultsRenderer<String, *>>(format) as HtmlIndividualEventResultsRenderer
    ) }
    bind<IndividualEventResultsRenderer<String, *>>(Format.HTML) with singleton {
        HtmlIndividualEventResultsRenderer()
    }
}