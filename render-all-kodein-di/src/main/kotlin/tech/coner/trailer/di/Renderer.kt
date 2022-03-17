package tech.coner.trailer.di

import org.kodein.di.DI

val allRendererModule = DI.Module("tech.coner.trailer.render") {
    importAll(
        jsonRenderModule,
        textRenderModule,
        htmlRenderModule
    )

}