package org.coner.trailer.di

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.asciitable.AsciiTableEventResultsReportColumnRendererFactory
import org.coner.trailer.render.asciitable.AsciiTableGroupedResultsReportRenderer
import org.coner.trailer.render.asciitable.AsciiTableOverallResultsReportRenderer
import org.coner.trailer.render.asciitable.AsciiTableResultsReportColumn
import org.kodein.di.*

val asciiTableModule = DI.Module("org.coner.trailer.render.asciitable") {
    bind<AsciiTableEventResultsReportColumnRendererFactory>() with singleton { AsciiTableEventResultsReportColumnRendererFactory() }
    bind<List<AsciiTableResultsReportColumn>>() with multiton { columns: List<EventResultsReportColumn> ->
        instance<AsciiTableEventResultsReportColumnRendererFactory>().factory(columns)
    }
    bind<AsciiTableOverallResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> AsciiTableOverallResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<AsciiTableResultsReportColumn>>().invoke(columns)
    ) }
    bind<AsciiTableGroupedResultsReportRenderer>() with multiton { columns: List<EventResultsReportColumn> -> AsciiTableGroupedResultsReportRenderer(
        columns = factory<List<EventResultsReportColumn>, List<AsciiTableResultsReportColumn>>().invoke(columns)
    ) }
}