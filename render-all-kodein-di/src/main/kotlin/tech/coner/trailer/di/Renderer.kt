package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bindMultiton
import tech.coner.trailer.render.ParticipantRenderer
import tech.coner.trailer.render.RunRenderer
import tech.coner.trailer.render.text.TextParticipantRenderer
import tech.coner.trailer.render.text.TextRunRenderer
import kotlin.reflect.KClass

val allRendererModule = DI.Module("tech.coner.trailer.render") {
    importAll(
        jsonRenderModule,
        textRenderModule,
        htmlRenderModule
    )

    bindMultiton<Format, ParticipantRenderer> { when (it) {
        Format.TEXT -> TextParticipantRenderer()
        else -> unsupported(it, ParticipantRenderer::class)
    } }
    bindMultiton<Format, RunRenderer> { when (it) {
        Format.TEXT -> TextRunRenderer()
        else -> unsupported(it, RunRenderer::class)
    } }
}

private fun unsupported(format: Format, type: KClass<*>): Nothing = throw UnsupportedFormatException(format, type)