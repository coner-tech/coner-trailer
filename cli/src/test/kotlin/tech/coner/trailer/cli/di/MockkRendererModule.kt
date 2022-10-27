package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import tech.coner.trailer.di.Format
import tech.coner.trailer.render.ParticipantRenderer
import tech.coner.trailer.render.RunRenderer

// this maybe should go into a new "-test" module, but I want to eliminate/consolidate renderers into cli views
val mockkRendererModule = DI.Module("mockk for tech.coner.trailer.render") {
    bindMultiton<Format, RunRenderer> { _: Format -> mockk() }
    bindMultiton<Format, ParticipantRenderer> { _: Format -> mockk()  }
}