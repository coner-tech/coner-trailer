package exploratory.route

import io.ktor.server.http.content.resource
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticBasePackage
import io.ktor.server.routing.Route

fun Route.staticAssetRoutes() {
    static("/assets") {
        staticBasePackage = "tech/coner/trailer/webapp/static"
        resource("coner-trailer.css")
    }
}