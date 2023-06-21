package tech.coner.trailer.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.nio.file.Path
import java.nio.file.Paths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import net.harawata.appdirs.AppDirsFactory
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindMultiton
import org.kodein.di.bindSingleton
import org.kodein.di.factory
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton
import tech.coner.trailer.eventresults.EventResultsFileNameGenerator
import tech.coner.trailer.io.repository.ConfigurationRepository
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.util.FileOutputDestinationResolver
import tech.coner.trailer.io.util.SimpleCache

val ioModule = DI.Module("coner.trailer.cli.io") {
    bindMultiton { args: ConfigurationServiceArgument ->
        ConfigurationService(
            coroutineContext = Dispatchers.IO + Job(),
            repository = ConfigurationRepository(
                objectMapper = ObjectMapper()
                    .registerKotlinModule()
                    .enable(SerializationFeature.INDENT_OUTPUT),
                configDir = args.configDir
            ),
            cache = SimpleCache()
        )
    }
    bind { scoped(EnvironmentScope).singleton {
        factory<ConfigurationServiceArgument, ConfigurationService>()(context.configurationServiceArgument)
    } }
    bindSingleton<FileOutputDestinationResolver> { FileOutputDestinationResolver(
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