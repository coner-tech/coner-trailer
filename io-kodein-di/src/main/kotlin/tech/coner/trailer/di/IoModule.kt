package tech.coner.trailer.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.harawata.appdirs.AppDirsFactory
import org.kodein.di.*
import tech.coner.trailer.eventresults.EventResultsFileNameGenerator
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import java.nio.file.Path
import java.nio.file.Paths

val ioModule = DI.Module("coner.trailer.cli.io") {
    bindMultiton { args: ConfigurationServiceArgument ->
        ConfigurationService(
            objectMapper = ObjectMapper()
                .registerKotlinModule()
                .enable(SerializationFeature.INDENT_OUTPUT),
            configDir = args.configDir
        )
    }
    bind<FileOutputDestinationResolver>() with singleton { FileOutputDestinationResolver(
        eventResultsFileNameGenerator = instance()
    ) }
    bindSingleton { EventResultsFileNameGenerator() }
}

sealed class ConfigurationServiceArgument {
    abstract val configDir: Path
    object Default : ConfigurationServiceArgument() {
        override val configDir: Path = AppDirsFactory.getInstance()
            .let { appDirs -> Paths.get(appDirs.getUserConfigDir("coner-trailer", "1.0", "coner")) }
    }
    data class Override(override val configDir: Path) : ConfigurationServiceArgument()
}
typealias ConfigurationServiceFactory = (ConfigurationServiceArgument) -> ConfigurationService