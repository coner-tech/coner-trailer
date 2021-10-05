package org.coner.trailer.di

import org.kodein.di.DI

val allRendererModule = DI.Module("org.coner.trailer.render") {
    importAll(
        jsonRenderModule,
        textRenderModule,
        htmlRenderModule
    )

}