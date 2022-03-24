package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import tech.coner.trailer.io.service.ConfigurationService

val mockkIoModule = DI.Module("coner.trailer.io.mockk") {
    bind<ConfigurationService>() with multiton { arg: ConfigurationServiceArgument -> mockk() }
}