package org.coner.trailer.di

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.html.HtmlEventResultsReportColumnRendererFactory
import org.coner.trailer.render.html.HtmlGroupedResultsReportRenderer
import org.coner.trailer.render.html.HtmlOverallResultsReportRenderer
import org.coner.trailer.render.html.HtmlResultsReportColumn
import org.kodein.di.*

val htmlRenderModule = DI.Module("org.coner.trailer.render.html") {
    bind<HtmlEventResultsReportColumnRendererFactory>() with singleton { HtmlEventResultsReportColumnRendererFactory() }
    bind<List<HtmlResultsReportColumn>>() with multiton { columns: List<EventResultsReportColumn> ->
        instance<HtmlEventResultsReportColumnRendererFactory>().factory(columns)
    }
    bind<HtmlOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> HtmlOverallResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<HtmlResultsReportColumn>>().invoke(columns)
    ) }
    bind<HtmlGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> HtmlGroupedResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<HtmlResultsReportColumn>>().invoke(columns)
    ) }
}