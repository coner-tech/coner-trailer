package org.coner.trailer.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.harawata.appdirs.AppDirsFactory
import org.coner.trailer.eventresults.EventResultsFileNameGenerator
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.io.util.FileOutputDestinationResolver
import org.kodein.di.*
import java.nio.file.Path
import java.nio.file.Paths

val ioModule = DI.Module("coner.trailer.cli.io") {
    bind<ConfigurationService>() with multiton { arg: ConfigurationServiceArgument ->
        val appDirs by lazy { AppDirsFactory.getInstance() }
        ConfigurationService(
            objectMapper = ObjectMapper()
                .registerKotlinModule()
                .enable(SerializationFeature.INDENT_OUTPUT),
            configDir = when (arg) {
                ConfigurationServiceArgument.Default -> Paths.get(appDirs.getUserConfigDir("coner-trailer", "1.0", "coner"))
                is ConfigurationServiceArgument.Override -> arg.configDir
            }
        )
    }
    bind<FileOutputDestinationResolver>() with singleton { FileOutputDestinationResolver(
        eventResultsFileNameGenerator = instance()
    ) }
    bindSingleton { EventResultsFileNameGenerator() }
}

sealed class ConfigurationServiceArgument {
    object Default : ConfigurationServiceArgument()
    data class Override(val configDir: Path) : ConfigurationServiceArgument()
}
typealias ConfigurationServiceFactory = (ConfigurationServiceArgument) -> ConfigurationService