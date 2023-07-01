package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import tech.coner.trailer.Policy
import tech.coner.trailer.di.Format
import tech.coner.trailer.render.ParticipantsRenderer
import tech.coner.trailer.render.RunsRenderer

// this maybe should go into a "testutil/mockk-render-text" module
val mockkRendererModule = DI.Module("mockk for tech.coner.trailer.render") {
    bindMultiton<Policy, RunsRenderer>(Format.TEXT) { _: Policy -> mockk() }
    bindMultiton<Policy, ParticipantsRenderer>(Format.TEXT) { _: Policy -> mockk() }
}