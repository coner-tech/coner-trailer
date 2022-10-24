package tech.coner.trailer.webapp.results

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.Hook
import io.ktor.server.application.call
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelinePhase

val ResourcesValidation = createRouteScopedPlugin(
    "ResourcesValidation"
) {
    on(ResourcesValidationHook) { call ->
        println("on ResourcesValidationHook handler")
        try {
            call.attributes[AttributeKey("ResourceInstance")]
        } catch (_: IllegalStateException) {
            // attr not found
        }
    }
}

object ResourcesValidationHook : Hook<suspend (ApplicationCall) -> Unit> {

    private val resourcesValidationPhase = PipelinePhase("Validation")

    override fun install(
        pipeline: ApplicationCallPipeline,
        handler: suspend (ApplicationCall) -> Unit
    ) {
        pipeline.insertPhaseAfter(ApplicationCallPipeline.Plugins, resourcesValidationPhase)
        pipeline.intercept(resourcesValidationPhase) { handler(call) }
    }
}
