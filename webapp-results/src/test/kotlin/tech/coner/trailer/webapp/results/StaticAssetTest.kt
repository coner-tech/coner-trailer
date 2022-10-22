package tech.coner.trailer.webapp.results

import assertk.all
import assertk.assertThat
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import io.ktor.client.request.get
import io.ktor.http.ContentType
import org.junit.jupiter.api.Test
import tech.coner.trailer.assertk.ktor.bodyAsText
import tech.coner.trailer.assertk.ktor.contentType
import tech.coner.trailer.assertk.ktor.hasSameMainContentType
import tech.coner.trailer.assertk.ktor.isSuccess
import tech.coner.trailer.assertk.ktor.status

class StaticAssetTest {

    @Test
    fun `It should serve coner trailer stylesheet`() = testResultsWebapp {
        val url = "/assets/coner-trailer.css"

        val actual = client.get(url)

        assertThat(actual).all {
            status().isSuccess()
            contentType().isNotNull().hasSameMainContentType(ContentType.Text.CSS)
            bodyAsText().isNotEmpty()
        }
    }

    @Test
    fun `It should serve webjar bootstrap js`() = testResultsWebapp {
        val url = "/assets/bootstrap/bootstrap.bundle.min.js"

        val actual = client.get(url)

        assertThat(actual).all {
            status().isSuccess()
            contentType().isNotNull().hasSameMainContentType(ContentType.Application.JavaScript)
            bodyAsText().isNotEmpty()
        }
    }
}