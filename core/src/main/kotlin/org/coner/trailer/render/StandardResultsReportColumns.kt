package org.coner.trailer.render

import org.coner.trailer.render.kotlinxhtml.KotlinxHtmlResultsReportColumn

val standardResultsReportColumns = listOf(
    KotlinxHtmlResultsReportColumn.Position(),
    KotlinxHtmlResultsReportColumn.Signage(),
    KotlinxHtmlResultsReportColumn.MobilePositionSignage(),
    KotlinxHtmlResultsReportColumn.Name(),
    KotlinxHtmlResultsReportColumn.CarModel(),
    KotlinxHtmlResultsReportColumn.MobileNameCarModel(),
    KotlinxHtmlResultsReportColumn.Score(),
    KotlinxHtmlResultsReportColumn.DiffFirst(),
    KotlinxHtmlResultsReportColumn.DiffPrevious(),
    KotlinxHtmlResultsReportColumn.Runs()
)