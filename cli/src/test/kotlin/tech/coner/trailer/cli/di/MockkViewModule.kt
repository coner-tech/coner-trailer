package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.view.WebappConfigurationView

val mockkViewModule = DI.Module("tech.coner.trailer.cli.mockkViews") {
    bindSingleton<WebappConfigurationView> { mockk() }
}