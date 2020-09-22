package org.coner.trailer.cli.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory
import org.coner.trailer.cli.io.ConfigurationService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import java.nio.file.Path
import java.nio.file.Paths

val ioModule = DI.Module("io") {
    bind<ConfigurationService>() with singleton { ConfigurationService(
            appDirs = AppDirsFactory.getInstance(),
            objectMapper = ObjectMapper()
                    .registerKotlinModule()
                    .enable(SerializationFeature.INDENT_OUTPUT)
    ) }
}