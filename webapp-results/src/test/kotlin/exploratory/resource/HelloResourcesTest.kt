package exploratory.resource

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.resources.href
import io.ktor.resources.serialization.ResourcesFormat
import org.junit.jupiter.api.Test
import tech.coner.trailer.assertk.ktor.bodyAsText
import tech.coner.trailer.assertk.ktor.hasContentTypeIgnoringParams
import tech.coner.trailer.assertk.ktor.status

class HelloResourcesTest {

    @Test
    fun `It should say hello world`() = testExploratoryResources {
        val url = href(ResourcesFormat(), HelloResource())

        val actual = client.get(url)

        assertThat(actual).all {
            status().isEqualTo(HttpStatusCode.OK)
            hasContentTypeIgnoringParams(ContentType.Text.Html)
            bodyAsText().contains("Hello World")
        }
    }

    @Test
    fun `It should say hello to subject`() = testExploratoryResources {
        val url = href(ResourcesFormat(), HelloResource(to = "subject"))

        val actual = client.get(url)

        assertThat(actual).all {
            status().isEqualTo(HttpStatusCode.OK)
            hasContentTypeIgnoringParams(ContentType.Text.Html)
            bodyAsText().contains("Hello subject")
        }
    }
}