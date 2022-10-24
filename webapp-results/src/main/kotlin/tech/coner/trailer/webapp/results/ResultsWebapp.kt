package tech.coner.trailer.webapp.results

import exploratory.exploratoryModule
import exploratory.route.exploratoryRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.webjars.Webjars
import org.kodein.di.DI
import org.kodein.di.ktor.di
import tech.coner.trailer.io.WebappConfiguration
import tech.coner.trailer.webapp.results.route.staticAssetRoutes

fun resultsWebapp(di: DI, config: WebappConfiguration) {
    embeddedServer(CIO, port = config.port) {
        di {
            extend(di)
            import(exploratoryModule)
            // scaffold
        }
        resultsWebappModule()
    }
        .start(wait = true)
}

fun Application.resultsWebappModule() {
    install(Resources)
    install(Webjars) {
        path = "assets"
    }
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
    }
    routing {
        staticAssetRoutes()
        exploratoryRoutes()
    }
}