package tech.coner.trailer.di

import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindMultiton
import org.kodein.di.instance
import tech.coner.trailer.render.ParticipantRenderer
import tech.coner.trailer.render.Renderer
import tech.coner.trailer.render.text.TextParticipantRenderer
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
}

private fun unsupported(format: Format, type: KClass<*>): Nothing = throw UnsupportedFormatException(format, type)