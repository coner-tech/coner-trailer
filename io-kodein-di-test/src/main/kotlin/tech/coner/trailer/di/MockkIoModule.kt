package tech.coner.trailer.di

import io.mockk.mockk
import org.kodein.di.*
import tech.coner.trailer.io.service.ConfigurationService

val mockkIoModule = DI.Module("coner.trailer.io.mockk") {
    bindMultiton { _: ConfigurationServiceArgument -> mockk<ConfigurationService>() }
    bind { scoped(EnvironmentScope).singleton {
        factory<ConfigurationServiceArgument, ConfigurationService>().invoke(context.configurationServiceArgument)
    } }
}