package tech.coner.trailer.di

import org.kodein.di.*
import tech.coner.trailer.io.service.ConfigurationService

val mockkIoModule = DI.Module("coner.trailer.io.mockk") {
    bind { scoped(EnvironmentScope).singleton {
        factory<ConfigurationServiceArgument, ConfigurationService>().invoke(context.configurationServiceArgument)
    } }
}