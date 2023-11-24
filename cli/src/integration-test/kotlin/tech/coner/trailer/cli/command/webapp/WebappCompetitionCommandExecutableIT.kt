package tech.coner.trailer.cli.command.webapp

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.set
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import tech.coner.trailer.assertk.ktor.bodyAsText
import tech.coner.trailer.assertk.ktor.hasContentTypeIgnoringParams
import tech.coner.trailer.assertk.ktor.status
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.findWebappPort

@EnabledIfEnvironmentVariable(named = "FEATURE_WEBAPP_ENABLE", matches = "true")
class WebappCompetitionCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should start webapp competition server`() = runTest {
        newArrange { configDatabaseAdd("webapp-competition") }

        val response = newTestCommandAsync { webappCompetition(port = 0, exploratory = true) }
            .runWebappTest { client ->
                client.get("/hello")
            }

        assertThat(response).all {
            status().isEqualTo(HttpStatusCode.OK)
            hasContentTypeIgnoringParams(ContentType.Text.Html)
            bodyAsText().contains("Hello World")
        }
    }

    @Test
    fun `It should respond with static assets`() = runTest {
        newArrange { configDatabaseAdd("webapp-competition-static-assets") }

        newTestCommandAsync { webappCompetition(port = 0, exploratory = true) }
            .runWebappTest { client ->
                assertThat(client.get("/assets/coner-trailer.css")).all {
                    status().isEqualTo(HttpStatusCode.OK)
                    hasContentTypeIgnoringParams(ContentType.Text.CSS)
                    bodyAsText().isNotEmpty()
                }
            }
    }

    @Test
    fun `It should respond with webjar assets`() = runTest {
        newArrange { configDatabaseAdd("webapp-competition-webjar-assets") }

        newTestCommandAsync { webappCompetition(port = 0) }
            .runWebappTest { client ->
                assertThat(client.get("/assets/bootstrap/bootstrap.bundle.min.js")).all {
                    status().isEqualTo(HttpStatusCode.OK)
                    hasContentTypeIgnoringParams(ContentType.Application.JavaScript)
                    bodyAsText().isNotEmpty()
                }
            }
    }

    private fun <T> Process.runWebappTest(fn: suspend (HttpClient) -> T): T {
        return try {
            val port = await
                .untilNotNull { findWebappPort() }
            HttpClient(CIO) {
                defaultRequest {
                    url {
                        set(
                            scheme = "http",
                            host = "localhost",
                            port = port.toInt()
                        )
                    }
                }
            }
                .use { runBlocking { fn(it) } }
        } finally {
            destroy()
        }
    }
}