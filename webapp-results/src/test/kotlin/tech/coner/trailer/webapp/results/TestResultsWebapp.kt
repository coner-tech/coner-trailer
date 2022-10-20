package tech.coner.trailer.webapp.results

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.kodein.di.ktor.di

fun testResultsWebapp(fn: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
    application {
        scaffoldResultsWebapp()
    }
    fn()
}

fun Application.scaffoldResultsWebapp() {
    di {
        // scaffold
    }
    install(Resources)
    routing {
        // scaffold
    }
}