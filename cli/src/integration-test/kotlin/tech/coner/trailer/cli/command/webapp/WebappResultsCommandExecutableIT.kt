package tech.coner.trailer.cli.command.webapp

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
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
import tech.coner.trailer.assertk.ktor.bodyAsText
import tech.coner.trailer.assertk.ktor.hasContentTypeIgnoringParams
import tech.coner.trailer.assertk.ktor.status
import tech.coner.trailer.cli.command.BaseExecutableIT
import tech.coner.trailer.cli.util.findWebappPort

class WebappResultsCommandExecutableIT : BaseExecutableIT() {

    @Test
    fun `It should start webapp results`() = runTest {
        arrange { configDatabaseAdd("webapp-results") }

        val response = testCommandAsync { webappResults(port = 0, exploratory = true) }
            .runWebappTest { client ->
                client.get("/hello")
            }

        assertThat(response).all {
            status().isEqualTo(HttpStatusCode.OK)
            hasContentTypeIgnoringParams(ContentType.Text.Html)
            bodyAsText().contains("Hello World")
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