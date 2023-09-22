package tech.coner.trailer.cli.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.service.FeatureService

val mockkCliServiceModule = DI.Module("tech.coner.trailer.cli.service mockk") {
    bindSingleton<FeatureService> { mockk() }
}