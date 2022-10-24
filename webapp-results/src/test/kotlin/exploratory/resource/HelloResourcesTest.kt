package exploratory.resource

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.ContentType.Text.Html
import io.ktor.http.ContentType.Text.Plain
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.parametersOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tech.coner.trailer.assertk.ktor.bodyAsText
import tech.coner.trailer.assertk.ktor.hasContentTypeIgnoringParams
import tech.coner.trailer.assertk.ktor.status
import tech.coner.trailer.webapp.results.testResultsWebapp

class HelloResourcesTest {

    private val url = "/hello"

    @Nested
    inner class Get {

        @Test
        fun `It should get hello world`() = testResultsWebapp { client ->
            val actual = client.get(url)

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.OK)
                hasContentTypeIgnoringParams(Html)
                bodyAsText().contains("Hello World")
            }
        }

        @Test
        fun `It should get hello world with query param to subject`() = testResultsWebapp { client ->
            val actual = client.get(url) {
                parameter("to", "subject")
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.OK)
                hasContentTypeIgnoringParams(Html)
                bodyAsText().contains("Hello subject")
            }
        }

        @Test
        fun `It should fail with bad request when query param excessive`() = testResultsWebapp { client ->
            val actual = client.get(url) {
                parameter("to", "f".repeat(100))
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.BadRequest)
                hasContentTypeIgnoringParams(Plain)
                bodyAsText().contains("query parameter", "excessive", ignoreCase = true)
            }
        }

        @Test
        fun `It should be not found when exploratory disabled`() = testResultsWebapp(exploratory = false) { client ->
            val actual = client.get(url)

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.NotFound)
            }
        }
    }

    @Nested
    inner class PostJson {

        private val url = "${this@HelloResourcesTest.url}/json"

        @Test
        fun `It should post json with hello world body to subject`() = testResultsWebapp { client ->
            val hello = HelloResource("subject")
            val actual = client.post(url) {
                contentType(Json)
                setBody(hello)
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.OK)
                hasContentTypeIgnoringParams(Json)
                bodyAsText().contains(hello.to)
            }
        }

        @Test
        fun `It should fail with bad request when post json with empty body`() = testResultsWebapp { client ->
            val actual = client.post(url) {
                contentType(Json)
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.BadRequest)
            }
        }

        @Test
        fun `It should fail with bad request when post json with nonsense body`() = testResultsWebapp { client ->
            val actual = client.post(url) {
                contentType(Json)
                setBody(mapOf("foo" to "bar"))
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.BadRequest)
            }
        }

        @Test
        fun `It should fail with bad request when post json with excessive subject`() = testResultsWebapp { client ->
            val actual = client.post(url) {
                contentType(Json)
                setBody(HelloResource("f".repeat(100)))
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.BadRequest)
                bodyAsText().contains("object", "excessive", ignoreCase = true)
            }
        }

        @Test
        fun `It should be not found when exploratory disabled`() = testResultsWebapp(exploratory = false) { client ->
            val actual = client.post(url) {
                contentType(Json)
                setBody(HelloResource("to"))
            }

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.NotFound)
            }
        }
    }

    @Nested
    inner class PostForm {

        private val url = "${this@HelloResourcesTest.url}/form"

        @Test
        fun `It should post json with hello world form to subject`() = testResultsWebapp { client ->
            val actual = client.submitForm(url, parametersOf("to", "subject"))

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.OK)
                hasContentTypeIgnoringParams(Plain)
                bodyAsText().contains("Hello subject")
            }
        }

        @Test
        fun `It should fail with bad request when post empty form`() = testResultsWebapp { client ->
            val actual = client.submitForm(url, parametersOf(emptyMap()))

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.BadRequest)
                hasContentTypeIgnoringParams(Plain)
                bodyAsText().contains("parameters", "missing", ignoreCase = true)
            }
        }

        @Test
        fun `It should fail with bad request when post form with excessive subject`() = testResultsWebapp { client ->
            val actual = client.submitForm(url, parametersOf("to" to listOf("f".repeat(100))))

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.BadRequest)
                bodyAsText().contains("parameters", "excessive", ignoreCase = true)
            }
        }

        @Test
        fun `It should be not found when exploratory disabled`() = testResultsWebapp(exploratory = false) { client ->
            val actual = client.submitForm(url, parametersOf("to", "hello"))

            assertThat(actual).all {
                status().isEqualTo(HttpStatusCode.NotFound)
            }
        }
    }
}