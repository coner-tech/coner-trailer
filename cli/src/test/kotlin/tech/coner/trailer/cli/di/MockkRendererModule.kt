package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindMultiton
import tech.coner.trailer.Policy
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.render.view.ParticipantsViewRenderer
import tech.coner.trailer.render.view.RunsViewRenderer

// this maybe should go into a "testutil/mockk-render-text" module
val mockkRendererModule = DI.Module("mockk for tech.coner.trailer.render") {
    bindMultiton<Policy, RunsViewRenderer>(Format.TEXT) { _: Policy -> mockk() }
    bindMultiton<Policy, ParticipantsViewRenderer>(Format.TEXT) { _: Policy -> mockk() }
}