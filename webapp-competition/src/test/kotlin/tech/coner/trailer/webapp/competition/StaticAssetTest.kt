package tech.coner.trailer.webapp.competition

import assertk.all
import assertk.assertThat
import assertk.assertions.isNotEmpty
import io.ktor.client.request.get
import io.ktor.http.ContentType
import org.junit.jupiter.api.Test
import tech.coner.trailer.assertk.ktor.bodyAsText
import tech.coner.trailer.assertk.ktor.hasContentTypeIgnoringParams
import tech.coner.trailer.assertk.ktor.isSuccess
import tech.coner.trailer.assertk.ktor.status

class StaticAssetTest {

    @Test
    fun `It should serve coner trailer stylesheet`() = testCompetitionWebapp { client ->
        val url = "/assets/coner-trailer.css"

        val actual = client.get(url)

        assertThat(actual).all {
            status().isSuccess()
            hasContentTypeIgnoringParams(ContentType.Text.CSS)
            bodyAsText().isNotEmpty()
        }
    }

    @Test
    fun `It should serve webjar bootstrap js`() = testCompetitionWebapp { client ->
        val url = "/assets/bootstrap/bootstrap.bundle.min.js"

        val actual = client.get(url)

        assertThat(actual).all {
            status().isSuccess()
            hasContentTypeIgnoringParams(ContentType.Application.JavaScript)
            bodyAsText().isNotEmpty()
        }
    }
}