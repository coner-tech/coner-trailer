package org.coner.trailer.cli.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.harawata.appdirs.AppDirsFactory
import org.coner.trailer.cli.io.ConfigurationService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val ioModule = DI.Module("io") {
    bind<ConfigurationService>() with singleton { ConfigurationService(
            appDirs = AppDirsFactory.getInstance(),
            objectMapper = ObjectMapper()
                    .registerKotlinModule()
                    .enable(SerializationFeature.INDENT_OUTPUT)
    ) }
}