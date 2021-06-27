package org.coner.trailer.di

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlEventResultsReportColumnRendererFactory
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlGroupedResultsReportRenderer
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlOverallResultsReportRenderer
import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlResultsReportColumn
import org.kodein.di.*

val kotlinxHtmlModule = DI.Module("org.coner.trailer.render.kotlinxhtml") {
    bind<KotlinxHtmlEventResultsReportColumnRendererFactory>() with singleton { KotlinxHtmlEventResultsReportColumnRendererFactory() }
    bind<List<KotlinxHtmlResultsReportColumn>>() with multiton { columns: List<EventResultsReportColumn> ->
        instance<KotlinxHtmlEventResultsReportColumnRendererFactory>().factory(columns)
    }
    bind<KotlinxHtmlOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> KotlinxHtmlOverallResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<KotlinxHtmlResultsReportColumn>>().invoke(columns)
    ) }
    bind<KotlinxHtmlGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> KotlinxHtmlGroupedResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<KotlinxHtmlResultsReportColumn>>().invoke(columns)
    ) }
}