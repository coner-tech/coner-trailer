package org.coner.trailer.seasonpoints

import org.junit.jupiter.api.Test

class KotlinxHtmlStandingsReportRendererTest {

    lateinit var renderer: KotlinxHtmlStandingsReportRenderer

    @Test
    fun `It should render content-only for LSCC2019Simplified report`() {
        renderer = KotlinxHtmlStandingsReportRenderer()

        val actual = renderer.renderContentOnly(TestStandingsReports.lscc2019Simplified)

        TODO("parse with jsoup and assert")
    }
}