package tech.coner.trailer.webapp.competition

import exploratory.exploratoryModule
import exploratory.route.exploratoryRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.webjars.Webjars
import org.kodein.di.DI
import org.kodein.di.ktor.di
import tech.coner.trailer.io.WebappConfiguration
import tech.coner.trailer.webapp.competition.route.staticAssetRoutes

fun competitionWebapp(di: DI, config: WebappConfiguration) =
    embeddedServer(CIO, port = config.port) {
        di {
            extend(di)
            import(exploratoryModule)
            // scaffold
        }
        competitionWebappModule(exploratory = config.exploratory)
    }
        .start(wait = true)

fun Application.competitionWebappModule(exploratory: Boolean) {
    install(CallLogging)
    install(Resources)
    install(Webjars)
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
        if (exploratory) {
            exploratoryRoutes()
        }
    }
}