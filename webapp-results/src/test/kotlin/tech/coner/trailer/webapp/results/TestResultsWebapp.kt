package tech.coner.trailer.webapp.results

import exploratory.exploratoryModule
import exploratory.service.HelloService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.coroutines.runBlocking
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di

fun testResultsWebapp(exploratory: Boolean = true, fn: suspend (HttpClient) -> Unit) = testApplication {
    application {
        di {
            import(exploratoryModule)
            bindSingleton { ::HelloService }
        }
        resultsWebappModule(exploratory = exploratory)
    }
    createClient {
        install(ContentNegotiation) {
            json()
        }
    }
        .use { runBlocking { fn(it) } }
}
