package org.coner.trailer.seasonpoints

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.jupiter.api.Test

class KotlinxHtmlStandingsReportRendererTest {

    lateinit var renderer: KotlinxHtmlStandingsReportRenderer

    @Test
    fun `It should render content-only for LSCC2019Simplified report`() {
        renderer = KotlinxHtmlStandingsReportRenderer()

        val actual = renderer.renderContentOnly(TestStandingsReports.lscc2019Simplified)

        val actualDocument = Jsoup.parseBodyFragment(actual)
        assertAll {
            val actualFragment = actualDocument.body().children().single()
            assertThat(actualFragment, "rendered fragment")
                    .transform("id") { it.attr("id") }
                    .isEqualTo("season-points-standings")

            val actualSections = actualDocument.select("section")
            assertThat(actualSections, "rendered sections").all {
                hasSize(3)
                index(0).all {
                    transform("only child") { it.children().single() }.all {
                        prop(Element::normalName).isEqualTo("table")
                        transform("caption") { it.select("caption").single() }.all {
                            prop("text") { it.text() }.isEqualTo("H Street")
                        }
                    }

                }
            }
        }
    }
}