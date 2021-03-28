package org.coner.trailer.cli.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.harawata.appdirs.AppDirsFactory
import org.coner.trailer.cli.util.FileOutputDestinationResolver
import org.coner.trailer.io.ConfigurationService
import org.kodein.di.*
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi

@OptIn(ExperimentalPathApi::class)
val ioModule = DI.Module("coner.trailer.cli.io") {
    bind<ConfigurationService>() with factory { arg: ConfigurationServiceArgument ->
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
        eventResultsReportFileNameGenerator = instance()
    ) }
}

sealed class ConfigurationServiceArgument {
    object Default : ConfigurationServiceArgument()
    data class Override(val configDir: Path) : ConfigurationServiceArgument()
}
typealias ConfigurationServiceFactory = (ConfigurationServiceArgument) -> ConfigurationService