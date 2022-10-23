package tech.coner.trailer.webapp.results

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.Hook
import io.ktor.server.application.call
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelinePhase

val ResourcesValidation = createRouteScopedPlugin<RequestValidationConfig>(
    "ResourcesValidation",
    ::RequestValidationConfig
) {
    on(ResourcesValidationHook) { call ->
        println("on ResourcesValidationHook handler")
        try {
            val resourceInstance = call.attributes[AttributeKey("ResourceInstance")]
            println(resourceInstance)
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
        println("ResourcesValidationHook.install")
        pipeline.insertPhaseAfter(ApplicationCallPipeline.Plugins, resourcesValidationPhase)
        pipeline.intercept(resourcesValidationPhase) { handler(call) }
    }
}