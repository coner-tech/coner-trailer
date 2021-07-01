package org.coner.trailer.di

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.text.TextEventResultsReportColumnRendererFactory
import org.coner.trailer.render.text.TextGroupedResultsReportRenderer
import org.coner.trailer.render.text.TextOverallResultsReportRenderer
import org.coner.trailer.render.text.TextResultsReportColumn
import org.kodein.di.*

val asciiTableModule = DI.Module("org.coner.trailer.render.asciitable") {
    bind<TextEventResultsReportColumnRendererFactory>() with singleton { TextEventResultsReportColumnRendererFactory() }
    bind<List<TextResultsReportColumn>>() with multiton { columns: List<EventResultsReportColumn> ->
        instance<TextEventResultsReportColumnRendererFactory>().factory(columns)
    }
    bind<TextOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> TextOverallResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<TextResultsReportColumn>>().invoke(columns)
    ) }
    bind<TextGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> TextGroupedResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<TextResultsReportColumn>>().invoke(columns)
    ) }
}