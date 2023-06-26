package tech.coner.trailer.cli.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.cli.service.FeatureService
import tech.coner.trailer.io.util.PropertiesReaderFactory

val cliServiceModule = DI.Module("coner.trailer.cli.service") {
    bindSingleton {
        FeatureService(
            propertiesReader = PropertiesReaderFactory("cli.properties").create()
        )
    }
}